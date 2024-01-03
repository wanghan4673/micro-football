import { defineStore } from 'pinia'

export const useGeneralStore = defineStore('general', {
  state: () => ({
      islogin: true,
      drafts:{
        title: '',
        content:'',
        img:[],
      },
      user:{
        username: '',
        useraccount: '',
        league: '',
      }
  }),
  persist: {
    enabled: true // true 表示开启持久化保存
  }
})