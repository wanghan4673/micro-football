<template>
    <div style="padding-bottom:50px">
        <TitleElement :mainTitle="titleText" :subTitle="subTitleText" style="padding:25px"/>
        <!-- <div class="search-element">
            <SearchBox :defaultText="searchDefaultText" />
        </div> -->
        <div class="text-text">请选择联赛</div>
        <div class="button-container">
            <ImageButton :imgurl='require("@/assets/img/bllogo.png")' initbackground="write" buttonname="德甲" @select-event="setLeagueSearch" @recover-event="removeLeagueSearch"/>
            <ImageButton :imgurl='require("@/assets/img/cslogo.png")' initbackground="write" buttonname="中超" @select-event="setLeagueSearch" @recover-event="removeLeagueSearch"/>
            <ImageButton :imgurl='require("@/assets/img/le1logo.png")' initbackground="write" buttonname="法甲" @select-event="setLeagueSearch" @recover-event="removeLeagueSearch"/>
            <ImageButton :imgurl='require("@/assets/img/pmlogo.png")' initbackground="write" buttonname="英超" @select-event="setLeagueSearch" @recover-event="removeLeagueSearch"/>
            <ImageButton :imgurl='require("@/assets/img/salogo.png")' initbackground="write" buttonname="意甲" @select-event="setLeagueSearch" @recover-event="removeLeagueSearch"/>
            <ImageButton :imgurl='require("@/assets/img/lllogo.png")' initbackground="write" buttonname="西甲" @select-event="setLeagueSearch" @recover-event="removeLeagueSearch" />
        </div>
        <div class="text-text">请选择时间</div>
        <div class="date-select">
            <a-space direction="vertical" :size="20">
                <a-date-picker v-model:value="dateRange" />
            </a-space>
        </div>
        <div class="search-element">
            <a-space direction="vertical">
                <a-space warp>
                    <a-button type="primary" @click="search">开始搜索</a-button>
                </a-space>
            </a-space>
        </div>
        <div class="list-content"  >
            <GameListComponent v-if="isSearch" :dataList="gameList"/>
        </div>
        
    </div>
</template>

<script>
import {ref} from 'vue'
import TitleElement from '@/components/TitleElement.vue'
import SearchBox from '@/components/SearchBox.vue'
import ImageButton from '@/components/ImageButton.vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import GameListComponent from '@/components/GameListComponent.vue'
export default{
    name:'PlayersView',
    components:{
        TitleElement,
        SearchBox,
        ImageButton,
        ElMessage,
        GameListComponent
    },
    setup(){
        const searchLeagueSet = new Set()
        const setLeagueSearch = League => {
            searchLeagueSet.add(League)
            console.log(searchLeagueSet)
        }
        const removeLeagueSearch = League => {
            searchLeagueSet.delete(League)
            console.log(searchLeagueSet)
        }
        const dateRange = ref();
        const BaseUrl = '/api/game'
        const titleText = ref('联赛赛事信息')
        const subTitleText = ref('实时更新，不容错过的联赛赛事一览')
        const searchDefaultText = ref('搜索联赛信息')
        const gameList = ref([])
        const isSearch = ref(false)
        const search = async () => {
            gameList.value = []
            isSearch.value = false
            if(searchLeagueSet.size == 0){
                ElMessage({
                    message: "请选择联赛",
                    type: "error"
                });
                return
            }
            if (dateRange.value === undefined){
                ElMessage({
                    message: "请选择日期",
                    type: "error"
                });
                return
            }
            let yearStr = `${dateRange.value.$y.toString()}`
            let monthStr = ''
            let dayStr = ''

            let month = dateRange.value.$M + 1
            if (month < 10){
                monthStr = `0${month.toString()}`
            }
            else{
                monthStr = `${month.toString()}`
            }
            if(dateRange.value.$D < 10){
                dayStr = `0${dateRange.value.$D}`
            }
            else{
                dayStr = `${dateRange.value.$D}`
            }
            let dateStr = `${yearStr}-${monthStr}-${dayStr}`
            console.log(dateStr)
            for(const league of searchLeagueSet){
                try{
                    const response = await axios.get(`${BaseUrl}?date=${dateStr}&leagueName=${league}`)//,formData)
                    console.log(response);
                    gameList.value.push(...response.data.data);
                    console.log('gameList', gameList.value);
                    isSearch.value = true
                } catch(err) {
                    ElMessage({
                        message: '获取失败',
                        type:'error'
                    });
                    console.error(err);
                }
            }
        }
        return{
            titleText,
            subTitleText,
            searchDefaultText,
            search,
            setLeagueSearch,
            removeLeagueSearch,
            dateRange,
            isSearch,
            gameList
        }
    }
}


</script>

<style lang="scss" scoped>
.search-element {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 50px;
}

.button-container {
  display: flex;
  gap: 0px; /* Optional: Adjust the gap between buttons */
  justify-content: center;
  align-items: center;
  padding:25px;
}

.text-text{
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight:bold;
    color: black;
    text-shadow: 2px 2px 4px rgba(0,0,0,0.2);
    padding:25px;
    margin-top:25px;
}

.date-select{
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 12px;
}

.list-content{
    min-height:500px;
}


</style>