import request from '@/utils/request'
export default {
  getHotInfo() {
    return request({
      url: `/eduservice/indexFront/getHotInfo`,
      method: 'get'
    })
  }
}