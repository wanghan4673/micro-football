import { defineStore } from 'pinia'

export const useForumStore = defineStore('forum', {
    persist: true, //开启持久化
    state: () => {
        return {
            keyword: '',
            sorttype: 'like',
            chosedPost: null,
            getPosts: false,
        }
    },
})