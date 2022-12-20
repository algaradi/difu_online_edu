import request from '@/utils/request'
export default {
  getBanner() {
    return request({
      url: `/servicecms/bannerClient/getAllBanner`,
      method: 'get'
    })
  }
}