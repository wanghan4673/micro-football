// 在登录注册页面时,topNav不展示
import { defineStore } from "pinia"

export const topNavStore = defineStore('topNav', {
    state: () => {
        return {
            topNavStatus: true,
            adminNavStatus:false,
        }
    },
    actions: {
        showTopNav() {
            this.topNavStatus = true
            this.adminNavStatus = false
        },
        showAdminNav(){
            this.topNavStatus = false
            this.adminNavStatus = true
        },
        bothNotShow() {
            this.topNavStatus = false
            this.adminNavStatus = false
        },
    }
})