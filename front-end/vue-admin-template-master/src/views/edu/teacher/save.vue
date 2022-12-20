<template>
  <div class="app-container">
    <template>
      <div class="app-container">
        <el-form label-width="120px">
          <el-form-item label="讲师名称">
            <el-input v-model="teacher.name"/>
          </el-form-item>
          <el-form-item label="讲师排序">
            <el-input-number v-model="teacher.sort" controls-position="right" :min="0"/>
          </el-form-item>
          <el-form-item label="讲师头衔">
            <el-select v-model="teacher.level" clearable placeholder="请选择">
              <!--
                数据类型一定要和取出的json中的一致，否则没法回填
                因此，这里value使用动态绑定的值，保证其数据类型是number
              -->
              <el-option :value="1" label="高级讲师"/>
              <el-option :value="2" label="首席讲师"/>
            </el-select>
          </el-form-item>
          <el-form-item label="讲师资历">
            <el-input v-model="teacher.career"/>
          </el-form-item>
          <el-form-item label="讲师简介">
            <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
          </el-form-item>
    
          <!-- 讲师头像：TODO -->
          <!-- 讲师头像 -->
            <el-form-item label="讲师头像">

              <!-- 头衔缩略图 -->
              <pan-thumb :image="String(teacher.avatar)"  alt="GeeksforGeeks logo" />
              <!-- 文件上传按钮 -->
              <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
              </el-button>

              <!--
            v-show：是否显示上传组件
            :key：类似于id，如果一个页面多个图片上传控件，可以做区分
            :url：后台上传的url地址
            @close：关闭上传组件
            @crop-upload-success：上传成功后的回调 -->
              <image-cropper
                            v-show="imagecropperShow"
                            :width="300"
                            :height="300"
                            :key="imagecropperKey"
                            :url="BASE_API+'/eduOss/fileoss/avatarUpload'"
                            field="file"
                            @close="close"
                            @crop-upload-success="cropSuccess"/>

            </el-form-item>
                
          <el-form-item>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdateTeacher">保存</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
  </div>
</template>
<script>
  import teacherApi from '@/api/edu/teacher'
  //上传头像组件引入
  import ImageCropper from '@/components/ImageCropper'
  import PanThumb from '@/components/PanThumb'
   export default{
    //上传头像声明
    components: { ImageCropper, PanThumb },
     data(){
      return {
         teacher:{
          name: '',
          sort: 0,  
          level: 1,
          career: '',
          intro: '',
          avatar: ''
       
         },
          BASE_API: process.env.BASE_API, // 接口API地址
          imagecropperShow: false, // 是否显示上传组件
          imagecropperKey: 0, // 上传组件id
         saveBtnDisabled:false
      }
     },
     created(){

     this.init()

     },
     watch:{// 监听
      //监听路由变化--解决访问统一路径的页面渲染
      $route(to,from){
          this.init()
      }
     },
     
     methods:{

          //上传头像窗口关闭  
          close(){
            this.imagecropperShow = false
            // 上传失败后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
            this.imagecropperKey = this.imagecropperKey + 1
          },
          //上传头像保存事件
          cropSuccess(data){
            this.imagecropperShow = false
            this.teacher.avatar = data.avatarUrl
            // 上传成功后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
            this.imagecropperKey = this.imagecropperKey + 1
          },
         // 页面加载数据初始化
         init(){
           //如何有id值就加载数据，没有就清楚表单信息
           if( this.$route.params && this.$route.params.id){
             const id = this.$route.params.id
             this.getTeacherInfo(id)
           }else{
             this.teacher={}
           }
         },
         //修改或添加方法
          saveOrUpdateTeacher(){
           //判断是否有id 有就执行修改操作，则添加操作
           if(! this.teacher.id){
               //添加讲师
               this.addTeacher()
           }else{
                this.updateTeacherInfo()
           }
        
            //修改讲师

          },
          //添加讲师方法
          addTeacher(){
            teacherApi.saveTeacher(this.teacher)
            .then(response=>{
              //提示讲师信息添加成功
              this.$message({
                type: 'success',
                message: '讲师信息添加成功!'
              })
              //然后回到列表  路由跳转
              this.$router.push({path:'/teacher/table'})
            })
          },
          //根据id获取信息
          getTeacherInfo(id){
            teacherApi.getTeacherInfoById(id)
            .then(response =>{
              this.teacher =response.data.teacher
             
              console.log(this.teacher)
            })
          },
          updateTeacherInfo(){
            teacherApi.updateTeacherInfo(this.teacher)
            .then(response=>{
               //弹出修改成功提示
               this.$message({
                         type: 'success',
                         message: '修改成功!'
                         })
                         //回到列表页面 路由跳转
                     this.$router.push({path:'/teacher/table'})
            }).catch(()=>{
              //弹出修改失败提示
              this.$message({
                         type: 'error',
                         message: '修改失败!'
                         })
            })
          }

 

     }


   }

</script>