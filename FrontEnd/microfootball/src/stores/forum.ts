import { defineStore } from 'pinia'

export const useForumStore = defineStore('forum', {
    state: () => ({
        keyword: '',
        sorttype: 'like',
        sorttag: '',
        chosedPost: null,
        getPosts: false,
        chosedPage: 1,
        newedpost: false
    }),
    persist: {
      enabled: true // true 表示开启持久化保存
    }
})