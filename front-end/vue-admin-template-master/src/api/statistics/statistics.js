import request from '@/utils/request'

const api_name = '/servicestatistics/statistics-daily'
export default {

  // 生成数据---分析。
  createStatistics(day) {
    return request({
      url: `${api_name}/${day}`,
      method: 'post'
    })
  },

  // 获取数据---分析
  showChart(searchObj) {
    return request({
        url: `${api_name}/show-chart/${searchObj.begin}/${searchObj.end}/${searchObj.type}`,
        method: 'get'
    })
}


}