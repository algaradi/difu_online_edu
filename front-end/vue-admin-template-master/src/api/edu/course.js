import request from '@/utils/request'

export default{
    addCourseIno(courseInfo) {
        return request({
          url: `/eduservice/course/addCourseInfo`,
          method:'post',
          data:courseInfo
        
        })
    },
    // 根据课程id获取课程信息
    getCourseInfoById(courseId){
      return request({
        url: `/eduservice/course/getCourseIno/${courseId}`,
        method:'get'
      
      })
    },
    // 修改课程信息
    updateCourseInfo(courseInfo) {
      return request({
        url: `/eduservice/course/updateCourseInfo`,
        method:'post',
        data:courseInfo
      
      })
  },
  // 课程最终发布
  getPublishCourseInfo(id){
    return request({
      url: `/eduservice/course/getCoursePublish/${id}`,
      method:'get'
    
    })
  },
     // 发布课程最终信息
     publishCourse(courseId) {
      return request({
        url: `/eduservice/course/publishCourse/${courseId}`,
        method:'put'
      
      })
    },
    //查询功能
    getPageList(page, limit, searchObj) {
      return request({
        url: `/eduservice/course/${page}/${limit}`,
        method: 'get',
        params: searchObj
      })
    },
    // 删除课程信息
    removeById(id) {
      return request({
          url: `/eduservice/course/${id}`,
          method: 'delete'
      })
  }
    
}
