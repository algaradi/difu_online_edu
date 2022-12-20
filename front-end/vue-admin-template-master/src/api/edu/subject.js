import request from '@/utils/request'

export default{
    getAllSubjectTree() {
        return request({
          url: `/eduservice/subject/getAllSubjectTree`,
          method:'get'
        
        })
    }
    
}
