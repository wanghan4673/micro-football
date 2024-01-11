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
        userid: -1,
        league: '',
      },
      leagueChoice: '',
      keyword: '',
  }),
  persist: {
    enabled: true // true 表示开启持久化保存
  }
})