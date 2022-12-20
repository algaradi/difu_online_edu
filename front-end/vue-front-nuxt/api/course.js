import request from '@/utils/request'
export default {

 // 分页条件查询方法 
  getPageList(page, limit, searchObj) {
    return request({
      url: `/eduservice/courseFront/getCourseInfoList/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  // 获取课程二级分类
  getAllSubject() {
    return request({
      url: `/eduservice/subject/getAllSubjectTree`,
      method: 'get'
    })
  },

  // 根据课程id获取课程详情信息
  getById(courseId) {
    return request({
        url: `/eduservice/courseFront/getCourseAllInfo/${courseId}`,
        method: 'get'
    })
}
}