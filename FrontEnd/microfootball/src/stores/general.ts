import { defineStore } from 'pinia'

export const useGeneralStore = defineStore('general', {
  state: () => {
    return {
      islogin: true,
      drafts:{
        title: '',
        content:'',
        img:[],
      },
      user:{
        username: '',
      }
    }
  },
})