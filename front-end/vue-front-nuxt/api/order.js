import request from '@/utils/request'

export default {
    
    //1、创建订单
    createOrder(courseId) {
        return request({
            url: `/orderservice/order/createOrder/${courseId}`,
            method: 'post'
        })
    },
    //2、根据id获取订单
    getOrderById(id) {
        return request({
            url: `/orderservice/order/getOrderInfo/${id}`,
            method: 'get'
        })
    },
    //3、生成微信支付二维码
    aliPayToPay(orderId) {
        return request({
            url: `/orderservice/paylog/alipay/pay/${orderId}`,
            method: 'post'
        })
    }
    // //4、根据id获取订单支付状态
    // queryPayStatus(cid) {
    //     return request({
    //         url: '/orderservice/log/queryPayStatus/'+cid,
    //         method: 'get'
    //     })
    // }
}