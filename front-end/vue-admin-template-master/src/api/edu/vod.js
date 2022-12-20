import request from '@/utils/request'

const api_name = '/eduvod/vedio'

export default {
// 更具删除阿里云中的视频
removeById(id) {
    return request({
      url: `${api_name}/deleteAliyunVideo/${id}`,
      method: 'delete'
    })
  } 

}