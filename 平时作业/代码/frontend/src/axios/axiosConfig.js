import axios from 'axios';

const instance = axios.create({
  baseURL: '/testurl',
  timeout: 1000000,
  //headers: {'X-Custom-Header': 'foobar'}
  headers: {
    // 设置后端需要的传参类型
    'X-Requested-With': 'XMLHttpRequest',
},
});



export function post(url, data) {
    return instance.post(url, data);
}

export function get(url, params) {
    return instance.get(url, {
        params: params
    });
}

export function put(url, data) {
    return instance.put(url, data);
}

export function del(url, data) {
    return instance.delete(url, {
        data: data
    });
}

