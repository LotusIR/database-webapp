import axios from 'axios'
import { Message } from "element-ui"

axios.interceptors.response.use(success=>{
    if (success.status && success.status == 200) {
        if (success.data.code==500||success.data.code==401||success.data.code==403) {
            Message.error({message:success.data.message});
            return;
        }
        if (success.data.message) {
            Message.success({message:success.data.message});
        }
    }
    return success.data;
},error=>{
    Message.error({message:"网络接口错误"})
})

let base = '';

export const postRequest = (url,params)=>{
    return axios({
        method: 'POST',
        url: `${base}${url}`,
        data: params
    })
}

export const getRequest = (url,params)=>{
    return axios({
        method: 'GET',
        url: `${base}${url}`,
        params: params
    })
}