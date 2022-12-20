import request from '@/utils/request'

export default{

 
    // 添加小节
  addVideo(video) {
      return request({
        url: `/eduservice/video/addVideo`,
        method:'post',
        data:video
      })
  },
  
  // 修改章节
  updateVideo(video) {
    return request({
      url: `/eduservice/video/updateVideo`,
      method:'post',
      data:video
    })
},
// 删除章节
deleteVideo(chapterId){
  return request({
    url: `/eduservice/video/${chapterId}`,
    method:'delete'
  })
},

getVideo(chapterId){
  return request({
    url: `/eduservice/video/getVideo/${chapterId}`,
    method:'get'
  })
}



}