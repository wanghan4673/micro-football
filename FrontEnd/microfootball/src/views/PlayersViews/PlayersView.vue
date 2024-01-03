<template>
    <div style="">
        <TitleElement :mainTitle="titleText" :subTitle="subTitleText"/>
        <div class="search-element">
            <SearchBox :defaultText="searchDefaultText" @search-event="search" @error-event="errorEvent"/>
        </div>
        <div class="button-container" style="padding-bottom:50px">
            <ImageButton :imgurl='require("@/assets/img/bllogo.png")' initbackground="write" buttonname="德甲" @select-event="setLeagueSearch" @recover-event="removeLeagueSearch"/>
            <ImageButton :imgurl='require("@/assets/img/cslogo.png")' initbackground="write" buttonname="中超" @select-event="setLeagueSearch" @recover-event="removeLeagueSearch"/>
            <ImageButton :imgurl='require("@/assets/img/le1logo.png")' initbackground="write" buttonname="法甲" @select-event="setLeagueSearch" @recover-event="removeLeagueSearch"/>
            <ImageButton :imgurl='require("@/assets/img/pmlogo.png")' initbackground="write" buttonname="英超" @select-event="setLeagueSearch" @recover-event="removeLeagueSearch"/>
            <ImageButton :imgurl='require("@/assets/img/salogo.png")' initbackground="write" buttonname="意甲" @select-event="setLeagueSearch" @recover-event="removeLeagueSearch"/>
            <ImageButton :imgurl='require("@/assets/img/lllogo.png")' initbackground="write" buttonname="西甲" @select-event="setLeagueSearch" @recover-event="removeLeagueSearch" />
        </div>
        <div class="list-content">
            <PlayerListComponent v-if="isSearch" :dataList="playerList"/>
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
import PlayerListComponent from '@/components/PlayerListComponent.vue'
export default{
    name:'PlayersView',
    components:{
        TitleElement,
        SearchBox,
        ImageButton,
        ElMessage,
        PlayerListComponent
    },
    setup(){

        const BaseUrl = '/api/player'
        const titleText = ref('联赛球员信息')
        const subTitleText = ref('数据驱动的联赛球员洞察')
        const searchDefaultText = ref('搜索球员信息')
        const searchLeagueSet = new Set()
        const isSearch = ref(false)
        const playerList = ref([])

        const setLeagueSearch = League => {
            searchLeagueSet.add(League)
            console.log(searchLeagueSet)
        }
        const removeLeagueSearch = League => {
            searchLeagueSet.delete(League)
            console.log(searchLeagueSet)
        }
        const search = async searchText => {
            playerList.value = []
            isSearch.value = false
            if(searchLeagueSet.size == 0){
                
                ElMessage({
                    message: "请选择联赛",
                    type: "error"
                });
                return
            }
            for(const league of searchLeagueSet){
                try{
                    const response = await axios.get(`${BaseUrl}?leagueName=${league}&searchKey=${searchText}`)//game/date=${dateStr}&leagueName=${league}`);
                    console.log(response);
                    playerList.value.push(...response.data.data);
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
        const errorEvent = text => {
            console.log('test', text)
            ElMessage({
                message: '搜索框为空',
                type:'error'
            });
        }

        return{
            titleText,
            subTitleText,
            searchDefaultText,
            setLeagueSearch,
            removeLeagueSearch,
            search,
            errorEvent,
            isSearch,
            playerList
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

.button-element {
    width: '20px';
    height: '10px'
}
.button-container {
  display: flex;
  gap: 0px; /* Optional: Adjust the gap between buttons */
  justify-content: center;
  align-items: center;
}


.list-content{
    min-height:450px;

}


</style>