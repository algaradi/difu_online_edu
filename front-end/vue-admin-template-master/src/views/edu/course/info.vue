<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>

    <el-form label-width="120px">

        <el-form-item label="课程标题">
          <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
        </el-form-item>
      
      <!-- 所属分类：级联下拉列表 -->
        <!-- 一级分类 -->
        <el-form-item label="课程类别">
            <el-select
            v-model="courseInfo.subjectParentId"
            placeholder="请选择一级分类" v-on:change="subjectLevelOneChanged">
            <el-option
                v-for="subject in subjectOneLevelList"
                :key="subject.id"
                :label="subject.title"
                :value="subject.id"/>
            </el-select>

             <!-- 二级级分类 -->
                <el-select
                v-model="courseInfo.subjectId"
                placeholder="请选择二级分类" >
                <el-option
                    v-for="subject in subjectTwoLevelList"
                    :key="subject.id"
                    :label="subject.title"
                    :value="subject.id"/>
                </el-select>
        </el-form-item>
           

      
        <!-- 课程讲师 -->
        <el-form-item label="课程讲师">
            <el-select
              v-model="courseInfo.teacherId"
              placeholder="请选择">
              <el-option
                v-for="teacher in teacherList"
                :key="teacher.id"
                :label="teacher.name"
                :value="teacher.id"/>
            </el-select>
          </el-form-item>
      
        <el-form-item label="总课时">
          <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>
      
     
         <!-- 课程简介-->
        <el-form-item label="课程简介">
          <tinymce :height="300" v-model="courseInfo.description"/>
        </el-form-item>
        <!-- 课程封面-->
        <el-form-item label="课程封面">

          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/eduOss/fileoss/coverUpload'"
            class="avatar-uploader">
            <img :src="courseInfo.cover" height="400px" width="600px">
          </el-upload>
        </el-form-item>
      
        <el-form-item label="课程价格">
          <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>
      
        <el-form-item>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
        </el-form-item>
      </el-form>
  </div>
</template>
<script>
    import courseApi from '@/api/edu/course'
    import teacherApi from '@/api/edu/teacher'
    import subjectApi from '@/api/edu/subject'
    import Tinymce from '@/components/Tinymce'
export default {
  components: { Tinymce },
  data() {
    return {
      saveBtnDisabled: false , // 保存按钮是否禁用
      courseInfo:{
        title: '',
        subjectId: '',
        teacherId: '',
        subjectParentId: '',
        lessonNum: 0,
        description: '',
        cover: '/static/26.jpg',
        price: 0
      },
      courseId:'',
      BASE_API: process.env.BASE_API ,// 接口API地址
      teacherList:[],
      subjectOneLevelList:[],
      subjectTwoLevelList:[]
    }
  },

  created() {
    
    // 数据初始化
    this.init()
    
  },
  watch:{// 监听
      //监听路由变化--解决访问统一路径的页面渲染
      $route(to,from){
          this.init()
      }
     },

  methods: {
    // 数据初始化方法
    init(){
     // 判断是否有courseId， 有就执行修改回显数据 没有就进行添加
     if(this.$route.params && this.$route.params.id){
        this.courseId  = this.$route.params.id
        this.getCourseInfoById()
       }else{
        //加载所有的课程讲师信息到下拉列表中
        this.getAllTeacher()
          //加载所有的一级分类信息到下拉列表中
        this.getAllOneLevelSubject()
        
       }
    },
    // 根据id获取课程信息  数据回显
    getCourseInfoById(){
      courseApi.getCourseInfoById(this.courseId)
      .then(respones=>{
         this.courseInfo =respones.data.courseInfo
        
        // 一级二级分类查询
         subjectApi.getAllSubjectTree()
         .then(respones =>{
          this.subjectOneLevelList = respones.data.list
          //遍历所有的一级分类
           for(var i=0; i < this.subjectOneLevelList.length; i++){
           // 获取每一个一级分类
           var oneSubject = this.subjectOneLevelList[i]
           //比较当前courseId的一级分类id
           if(this.courseInfo.subjectParentId == oneSubject.id){
            // 获取一级id 的所有二级id
            this.subjectTwoLevelList = oneSubject.children
             
           }
         }
         })
          //加载所有的课程讲师信息到下拉列表中
        this.getAllTeacher()
      })

     
    },

    //封面上传后
    handleAvatarSuccess(res,file){
      this.courseInfo.cover = res.data.coverUrl
    },
    beforeAvatarUpload(file){
      const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
    },
    // 方面上传之前跳转格式

    //绑定事件一级分类变化，二级分类值变化
    subjectLevelOneChanged(value){
       //id 是一级分类的id值
       //遍历所有的一级分类，包含二级分类
       for(let i =0 ; i < this.subjectOneLevelList.length;i++){
        // 判断value获取的一级值和列表中的所有一级同id的值
        if(value === this.subjectOneLevelList[i].id){
            //获取当前同一一级id值的所有二级值
            this.subjectTwoLevelList = this.subjectOneLevelList[i].children
            // 每次点击后情空之前的信息
            this.courseInfo.subjectId =''
        }

        //
       }
    },
    //获取所有的课程一级分类列表
    getAllOneLevelSubject(){

        subjectApi.getAllSubjectTree()
        .then(response =>{
            this.subjectOneLevelList = response.data.list
        })
        
    },
    //获取所有的讲师信息---实现下拉单
    getAllTeacher(){
        teacherApi.getAllteacher()
        .then(response=>{
            this.teacherList = response.data.items
        })
    },
    
    // 添加课程
    addCourse(){
      courseApi.addCourseIno(this.courseInfo)
        .then(response=>{
            this.$message({
              type: 'success',
              message: "添加课程信息成功"
            })

            this.$router.push({ path: '/course/chapter/'+response.data.courseId })
        })},
    // 修改课程
    updateCourse(){
      courseApi.updateCourseInfo(this.courseInfo)
        .then(response=>{
            this.$message({
              type: 'success',
              message: "修改课程信息成功"
            })

            this.$router.push({ path: '/course/chapter/'+this.courseId})
        })
    },
    // 提交信息上传课程信息方法
    saveOrUpdate() {
      // 判断是加还是修改
      if(this.courseInfo.id){
        this.updateCourse()
      }else{
       this.addCourse()
      }
        
    
    }
  }
}
</script>
<style scoped>
  .tinymce-container {
    line-height: 29px;
  }
  </style>