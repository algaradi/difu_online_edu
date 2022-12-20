<template>
  <div class="app-container">

 
   <!--查询表单-->
   <el-form :inline="true" :model="teacherQuery"  class="demo-form-inline">
    <el-form-item>
      <el-input v-model="teacherQuery.name" placeholder="讲师名"/>
    </el-form-item>

    <el-form-item>
      <el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
        <el-option :value="1" label="高级讲师"/>
        <el-option :value="2" label="首席讲师"/>
      </el-select>
    </el-form-item>

    <el-form-item label="添加时间">
      <el-date-picker
        v-model="teacherQuery.gmtCreate"
        type="datetime"
        placeholder="选择开始时间"
        value-format="yyyy-MM-dd HH:mm:ss"
        default-time="00:00:00"
      />
    </el-form-item>
    <el-form-item>
      <el-date-picker
        v-model="teacherQuery.gmtModified"
        type="datetime"
        placeholder="选择截止时间"
        value-format="yyyy-MM-dd HH:mm:ss"
        default-time="00:00:00"
      />
    </el-form-item>

    <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
    <el-button type="default" @click="resetData()">清空</el-button>
  </el-form>

      <!-- 表格 -->
        <el-table :data="list" style="width: 100%">

          <el-table-column  label="序号" align="center" width="70"> 
              <template slot-scope="scope">
                {{ (current - 1) * size + scope.$index +1 }}
              </template>
          </el-table-column>

        <el-table-column  prop="name" label="名称" width="80"/>

        <el-table-column   label="头街" width="80">
            <template slot-scope="scope" >
                {{ scope.row.level===1?'高级讲师':'首席讲师' }}
            </template>
        </el-table-column>

        <el-table-column  prop="intro" label="资历" />
        <el-table-column  label="头像" width="130" align="center" >
            <template slot-scope="scope" >
            <div class="demo-type">
              <el-avatar :size="80" >
                <img :src="scope.row.avatar" style="width: 80px; height: 80px;">
              </el-avatar>
            </div>
          </template>
        </el-table-column>
       
        <el-table-column  prop="gmtCreate" label="添加时间" width="160"/>

        <el-table-column  prop="sort" label="排序" width="60"/>

        <el-table-column label="操作" width="200" align="center">
            <template slot-scope="scope">
                <router-link :to="'/teacher/edit/'+scope.row.id">
                    <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
                </router-link>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
            </template>
         </el-table-column>
        
        </el-table>

      <!-- 分页 -->
      <el-pagination
        background
        :current-page="current"
        :page-size="size"
        :total="total"
        style="padding: 30px 0; text-align: center;"
        layout="total, prev, pager, next, jumper"
        @current-change="getList"/>

  </div>
</template>

<script>
 import  teacher  from '@/api/edu/teacher'

    export default {
     
        data(){
            return {
                // 定义变量和初始化
                list: null, //查询返回的结果
                current: 1, // 当前页数
                size: 10, // 每页显示数量
                total:0, // 总记录数
                teacherQuery: {
                   name:null,
                   level:null,
                   gmtCreate:null,
                   gmtModified:null
                }// 传递过去的查询对象
                
                 
            }
        },
        created(){
               //在页面渲染前调用
               // 调用获取teacher列表
               this.getList()
        },
        methods:{
            // 调用teacher.js 方法 获取数据
            //实现分页跳蛛，给的一个当前也的参数
            //获取列表和组合条件方法
            getList(current=1){  
              this.current = current
                teacher.getTeacherQueryPageList(this.current,this.size,this.teacherQuery)
                .then(response => {
                   this.list =  response.data.records
                   this.total = response.data.total
                console.log(this.list)
                console.log(this.total)
                })
              .catch(error => {
                console.log(error)
              })
            },
            //清楚方法
            resetData(){
                  //清楚功能 首先要清楚 然后再查询所有
                  this.teacherQuery = {}
                  this.getList()
            },
            //删除方法
            removeDataById(id){

                   this.$confirm('此操作将永久删除该讲师, 是否继续?', '提示', {
                      confirmButtonText: '确定',
                      cancelButtonText: '取消',
                      type: 'warning'
                    })
                    .then( () => {

                      // 确认后删除提交
                      teacher.deleteTeacherById(id)
                      .then(response =>{
                         //弹出删除成功提示
                         this.$message({
                         type: 'success',
                         message: '删除成功!'
                         })
                        // 删除成功时刷新列表
                        this.getList();
                      })
                      .catch(() => {
                         this.$message({
                         type: 'error',
                         message: '删除失败'
                         })  
                    })
                      })
                   .catch(() => {
                         this.$message({
                         type: 'info',
                         message: '取消删除'
                         })  
                    })
            }   


         }
        }
</script>