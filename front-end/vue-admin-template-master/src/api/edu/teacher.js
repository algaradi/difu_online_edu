import request from '@/utils/request'

export default{
    getTeacherQueryPageList(current,size,teacherQuery ) {
        return request({
          //  "/eduservice/teacher/pageQuery/{current}/{size}"
          // url 可以直接拼接变量 如 '/eduservice/teacher/pageQuery/'+current+'/'+size 
          // url 还可以用     ` ` 通过 ${} 直接拼接参数
          url: `/eduservice/teacher/pageQuery/${current}/${size}`,
          method:'post',
          data: teacherQuery
        
        })
    },
    //删除讲师功能
    deleteTeacherById(id){
      return request({
        url:`/eduservice/teacher/${id}`,
        method: 'delete'
      })
    },
    //添加新讲师功能
    saveTeacher(teacher){
      return request({
        url:`/eduservice/teacher/addTeacher`,
        method: 'post',
        data:teacher
      })
    },
    // 根据讲师id获取讲师信息
    getTeacherInfoById(id){
      return request({
        url:`/eduservice/teacher/getTeacher/${id}`,
        method: 'get'
      })
    },
    //修改讲师信息
    updateTeacherInfo(teacher){
      return request({
        url:`/eduservice/teacher/updateTeacher`,
        method: 'post',
        data:teacher
      })
    },
    //获取所有的讲师信息 ---下拉
     // 根据讲师id获取讲师信息
     getAllteacher(){
      return request({
        url:`/eduservice/teacher/findAll`,
        method: 'get'
      })
    }
}
