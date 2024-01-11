import { defineStore } from "pinia"

export const followNumStore = defineStore('followNum', {
    state: () => {
        return {
            shouldMinus: false,
        }
    },
    actions: {
        changeFollowNum(){
            this.shouldMinus = true
        },
        changeOver(){
            this.shouldMinus = false
        }
    }
})