import request from '@/utils/request'

export default {

  getPlayAuth(vid) {
    return request({
      url: `eduvod/vedio/getPlayAuth/${vid}`,
      method: 'get'
    })
  }

}