<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>


    <el-button type="text" @click="addDialog()">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
        <li
            v-for="chapter in chapterList"
            :key="chapter.id">
         
             <p>
                {{ chapter.title }}

                <span class="acts">
                  <el-button round @click.native="() => opneVideoDialog(chapter.id)" >添加课时</el-button>
                  <el-button round  @click.native="() => updateDialog(chapter.id)">编辑</el-button>
                  <el-button round @click.native="removeChapter(chapter.id)">删除</el-button>
                </span>
                
              </p>
            <!-- 视频 -->
            <ul class="chanpterList videoList">
                <li
                    v-for="video in chapter.children"
                    :key="video.id">
                  
                    <p>
                      
                        {{ video.title }}
                      
                        <span class="acts">
                          <el-button  type="primary" icon="el-icon-edit" round size="mini" @click="updateVideoDialog(video.id)">编辑</el-button>
                          <el-button  type="danger" icon="el-icon-delete" round size="mini" @click.native="removeVideo(video.id)">删除</el-button>
                        </span>
                    </p>
                    
                </li>
            </ul>
        </li>
    </ul>
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
   </div>


<!-- 添加和修改章节表单 -->
<el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
  <el-form :model="chapter" label-width="120px">
      <el-form-item label="章节标题">
          <el-input v-model="chapter.title"/>
      </el-form-item>
      <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
      </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
      <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
  </div>
</el-dialog>

<!-- 添加和修改课时表单 -->
<el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
  <el-form :model="video" label-width="120px">
    <el-form-item label="课时标题">
      <el-input v-model="video.title"/>
    </el-form-item>
    <el-form-item label="课时排序">
      <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
    </el-form-item>
    <el-form-item label="是否免费">
      <el-radio-group v-model="video.free">
        <el-radio :label="true">免费</el-radio>
        <el-radio :label="false">默认</el-radio>
      </el-radio-group>
    </el-form-item>

    <!-- 视频 -->
    <el-form-item label="上传视频">
    <el-upload
              :on-success="handleVodUploadSuccess"
              :on-remove="handleVodRemove"
              :before-remove="beforeVodRemove"
              :on-exceed="handleUploadExceed"
              :file-list="fileList"
              :action="BASE_API+'/eduvod/vedio/uploadAliyunVideo'"
              :limit="1"
              class="upload-demo">
        <el-button size="small" type="primary">上传视频</el-button>
        <el-tooltip placement="right-end">
            <div slot="content">最大支持1G,<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
            <i class="el-icon-question"/>
        </el-tooltip>
        </el-upload>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
    <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
  </div>
</el-dialog>


  </div>
</template>

<script>
import chapterApi from '@/api/edu/chapter'
import videoApi from '@/api/edu/video'
import VodApi from '@/api/edu/vod'


export default {
  data() {
    return {
      saveBtnDisabled: false ,// 保存按钮是否禁用
      chapterList: [],
      courseId:'',
      chapter:{
        title:'',
        sort:0
      },
      dialogChapterFormVisible: false,
      saveVideoBtnDisabled: false, // 课时按钮是否禁用
      dialogVideoFormVisible: false,
      chapterId: '', // 课时所在的章节id
      video: {// 课时对象
        title: '',
        sort: 0,
        free: 0,
        videoSourceId: ''
      },
      fileList: [],//上传文件列表
      BASE_API: process.env.BASE_API // 接口API地址

    }
  },

  created() {

    // 获取课程id
    if(this.$route.params && this.$route.params.id){
      this.courseId = this.$route.params.id
      this.getAllChapter()
    }

  },

  methods: {
      // 删除之前调用的方法
      beforeVodRemove(file , fileList){
         return this.$confirm(`确认要删除 ${file.name} 吗？`)
      },
      //点击确认删除后（handler）阿里云中的视频调用的方法
      handleVodRemove(){
        // 调用Api接口去删除阿里云的视频
        VodApi.removeById(this.video.videoSourceId).then(response=>{
          this.$message({
            type: 'success',
            message: response.message
          })
          // 清空文件例表
          this.fileList=[]
          // 清空视频id
          this.video.videoSourceId = ''
          // 清空视频的名称
          this.video.videoOriginalName = ''
        })
      },
      //成功回调
      handleVodUploadSuccess(response, file, fileList) {
        // 赋值视频id
        this.video.videoSourceId = response.data.videoId
        // 赋值视频名称
        this.video.videoOriginalName = file.name;
      },
     
      //视图上传多于一个视频
      handleUploadExceed(files, fileList) {
        this.$message.warning('想要重新上传视频，请先删除已上传的视频')
      },

    //===================================章节=================================================================

    //修改小节弹出框
    updateVideoDialog(chapterId){
      videoApi.getVideo(chapterId)
      .then(response =>{
        this.video = response.data.video
        this.opneVideoDialog()
      })
    },
    // 添加小节弹出框
    opneVideoDialog(chapterId){
      // 打开弹出框
      this.dialogVideoFormVisible= true
      //清空---->注意位置
      this.video = {}
       this.fileList = []

      //设置小节id
      this.video.chapterId = chapterId
      //设置课程id
      this.video.courseId = this.courseId
       
      
    },
    // 小节信息提交方法       
    saveOrUpdateVideo(){
      // 判断是添加还是修改
      if(!this.video.id){
        this.addVideo()
       }else{
        this.updateVideo()
       }

    },
    // 添加小节
    addVideo(){
        videoApi.addVideo(this.video)
        .then(response =>{
           // 关闭弹框
           this.dialogVideoFormVisible = false
              // 提示
            this.$message({
             type: 'success',
             message: '添加章节信息成功!'
            })
             // 刷新页面
           this.getAllChapter()
        })
    },
    // 修改小节
    updateVideo(){
      videoApi.updateVideo(this.video)
        .then(response =>{
           // 关闭弹框
           this.dialogVideoFormVisible = false
              // 提示
            this.$message({
             type: 'success',
             message: '添加章节信息成功!'
            })
             // 刷新页面
           this.getAllChapter()
        })
    },
    removeVideo(chapterId){
      this.$confirm('此操作将永久删除该小节, 是否继续?', '提示', {
                      confirmButtonText: '确定',
                      cancelButtonText: '取消',
                      type: 'warning'
                    })
                    .then( () => {

                      // 确认后删除提交
                      videoApi.deleteVideo(chapterId)
                      .then(response =>{
                         //弹出删除成功提示
                         this.$message({
                         type: 'success',
                         message: '删除成功!'
                         })
                        // 删除成功时刷新列表
                        this.getAllChapter();
                      })
                      })
                   .catch(() => {
                         this.$message({
                         type: 'info',
                         message: '取消删除'
                         })  
                    })
    },
    //===================================章节=================================================================
    // 删除章节
    removeChapter(chapterId){

      this.$confirm('此操作将永久删除该章节, 是否继续?', '提示', {
                      confirmButtonText: '确定',
                      cancelButtonText: '取消',
                      type: 'warning'
                    })
                    .then( () => {

                      // 确认后删除提交
                      chapterApi.deleteChapter(chapterId)
                      .then(response =>{
                         //弹出删除成功提示
                         this.$message({
                         type: 'success',
                         message: '删除成功!'
                         })
                        // 删除成功时刷新列表
                        this.getAllChapter();
                      })
                      })
                   .catch(() => {
                         this.$message({
                         type: 'info',
                         message: '取消删除'
                         })  
                    })
    },
    // 打开添加章节弹框
    addDialog(){
       this.dialogChapterFormVisible = true
       this.chapter.title = ''
       this.chapter.sort=0
    },
    // 打开修改章节弹框
    updateDialog(chapterId){
       this.dialogChapterFormVisible = true
       chapterApi.getChapter(chapterId)
       .then(response =>{
        this.chapter  = response.data.item
       })
    },
    // 添加章节信息方法
    addChapter(){
      this.chapter.courseId = this.courseId
            chapterApi.addChapter(this.chapter)
            .then(response => {
              // 关闭弹框
              this.dialogChapterFormVisible = false
              // 提示
            this.$message({
             type: 'success',
             message: '添加章节信息成功!'
            })
             // 刷新页面
           this.getAllChapter()
           })
          
    },
    //修改章节信息方法
    updateChapter(){
      this.chapter.courseId = this.courseId
            chapterApi.updateChapter(this.chapter)
            .then(response => {
              // 关闭弹框
              this.dialogChapterFormVisible = false
              // 提示
            this.$message({
             type: 'success',
             message: '修改章节信息成功!'
            })
           })
           // 刷新页面
           this.getAllChapter()
    },
  
    // 章节信息提交
    saveOrUpdate(){
      
       // 判断是添加还是修改
       if(!this.chapter.id){
        this.addChapter()
       }else{
        this.updateChapter()
       }
    },

    //根据课程id 查询所有的课程章节和小节
    getAllChapter(){
      chapterApi.getAllChapterList(this.courseId)
      .then(response =>{
         this.chapterList = response.data.chapters
      })
    },

    previous() {
      this.$router.push({ path: '/course/info/'+this.courseId })
    },

    next() {
      this.$router.push({ path: '/course/publish/'+this.courseId })
    }
  }
}
</script>
<style scoped>
  .chanpterList{
      position: relative;
      list-style: none;
      margin: 0;
      padding: 0;
  }
  .chanpterList li{
    position: relative;
  }
  .chanpterList p{
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
    overflow:hidden;
  }
  .chanpterList .acts {
      float: right;
      font-size: 14px;
      overflow:hidden;
  }
  
  .videoList{
    padding-left: 50px;
  }
  .videoList p{
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }
  
  </style>