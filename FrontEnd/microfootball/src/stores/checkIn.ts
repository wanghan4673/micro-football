import { defineStore } from "pinia"

export const checkInStore = defineStore('checkIn', {
    state: () => {
        return {
            shouldAddScore: false,
        }
    },
    actions: {
        changeScore(){
            this.shouldAddScore = true
        },
        changeOver(){
            this.shouldAddScore = false
        }
    }
})