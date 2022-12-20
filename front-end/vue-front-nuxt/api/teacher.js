import request from '@/utils/request'
export default {

    // 讲师分页查询
  getTeacherList(page, limit) {   
    return request({
      url: `/eduservice/teacherFront/getTeacherList/${page}/${limit}`,
      method: 'get'
    })
  },

  // 讲师详情查询
  
  getTeacherInfo(id){
    return request({
        url: `/eduservice/teacherFront/getTeacherInfo/${id}`,
        method: 'get'
      })
  }
}