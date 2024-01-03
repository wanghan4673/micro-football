<template>
    <div style="padding-bottom:50px">
        <TitleElement :mainTitle="titleText" :subTitle="subTitleText"/>
        <div class="search-element">
            <SearchBox :defaultText="searchDefaultText" @search-event="search" @error-event="errorEvent"/>
        </div>
        <div class="list-content">
            <TeamListComponent v-if="isSearch" :dataList="teamList"/>
        </div>
        
    </div>
</template>

<script>
import {ref} from 'vue'
import TitleElement from '@/components/TitleElement.vue'
import SearchBox from '@/components/SearchBox.vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import TeamListComponent from '@/components/TeamListComponent.vue'
export default{
    name:'PlayersView',
    components:{
        TitleElement,
        SearchBox,
        TeamListComponent
    },
    setup(){
        const BaseUrl = '/api/team'
        const titleText = ref('联赛球队信息')
        const subTitleText = ref('背后英雄，揭秘每支球队的独特魅力')
        const searchDefaultText = ref('搜索球队信息')
        const teamList = ref([])
        const isSearch = ref(false)

        const search = async text => {
            teamList.value = []
            isSearch.value = false
            try{
                const response = await axios.get(`${BaseUrl}?searchKey=${text}`)//game/date=${dateStr}&leagueName=${league}`);
                // const response = await axios.get(`http://localhost:8083/team?searchKey=${text}`)
                console.log(response);
                teamList.value.push(...response.data.data);
                console.log('gameList', teamList.value);
                isSearch.value = true
                console.log('isSearch', isSearch.value)
            } catch(err) {
                ElMessage({
                    message: '获取失败',
                    type:'error'
                });
                console.error(err);
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
            search,
            errorEvent,
            isSearch,
            teamList,
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

.list-content{
    min-height:500px;

}


</style>