import request from '@/utils/request'

export default{

    // 获取所有的章节和小节视频的列表
    getAllChapterList(courseId) {
        return request({
          url: `/eduservice/chapter/getAllChapter/${courseId}`,
          method:'get'
        })
    },
    // 添加章节
    addChapter(chapter) {
      return request({
        url: `/eduservice/chapter/addChapter`,
        method:'post',
        data:chapter
      })
  },
  // 根据Id查询
  getChapter(chapterId){
    return request({
      url: `/eduservice/chapter/getChapter/${chapterId}`,
      method:'get'
    })
  },
  // 修改章节
  updateChapter(chapter) {
    return request({
      url: `/eduservice/chapter/updateChapter`,
      method:'post',
      data:chapter
    })
},
// 删除章节
deleteChapter(chapterId){
  return request({
    url: `/eduservice/chapter/deleteChapter/${chapterId}`,
    method:'delete'
  })
}

    
}