<template>
    <!-- <TitleElement :mainTitle="titleText" :subTitle="subTitleText" /> -->
    <div style="display: flex; padding: 30px;">
        <PlayerSideNave></PlayerSideNave>
        <div style="padding-left: 20px;">
            <el-input style="height: 60px; max-width: 300px; padding: 10px;" v-model="store.keyword" placeholder="关键词"
                @keyup.enter="search">
                <template #suffix>
                    <el-icon class="el-input__icon">
                        <Search />
                    </el-icon>
                </template>
            </el-input>

            <div id="playercard">
                <PlayerItem v-for="player in playerList" :key="player" :player="player" @click="toplayerInfo(player)" />
            </div>
            <div style="display: flex;justify-content: center; margin: 20px;">
                <el-pagination @current-change="handleCurrentChange" background layout="prev, pager, next"
                    :total=totalElements
                    :page-size="pageSize"
                    :current-page= page />
            </div>
        </div>
    </div>
 
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import TitleElement from '@/components/TitleElement.vue'
import SearchBox from '@/components/SearchBox.vue'
import ImageButton from '@/components/ImageButton.vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import PlayerItem from '@/components/PlayerComponents/PlayerItem.vue'
import PlayerSideNave from '@/components/PlayerComponents/PlayerSideNav.vue'
import { useGeneralStore } from '../../stores/general.ts'

const store = useGeneralStore()
const BaseUrl = '/api/player'
const titleText = ref('联赛球员信息')
const subTitleText = ref('数据驱动的联赛球员洞察')
const searchDefaultText = ref('搜索球员信息')
let searchLeagueSet = ref[[]]
const isSearch = ref(false)
const router = useRouter()
const playerList = ref([])
let totalElements = ref(1)
let page = ref(1)
const pageSize = 20
const getplayer = async () => {
    // totalElements = 1
    playerList.value = []
    try {
        const response = await axios.get(`${BaseUrl}?page=${page.value}&size=${pageSize}&league=${store.leagueChoice}`)
        console.log(response)
        playerList.value.push(...response.data.data.playerSimpleInfos);
        totalElements.value = response.data.data.count;
    } catch (err) {
        ElMessage({
            message: '获取失败',
            type: 'error'
        });
        console.error(err);
    }
}

const handleCurrentChange = async (newPage) => {
    page.value = newPage
    getplayer()
}

onMounted(() => {
    store.leagueChoice = '全部赛事'
    getplayer()
})
watch(() => store.leagueChoice, (newleague) => {
    getplayer()
})

const search = async () => {
    if(store.keyword == null || store.keyword == ""){
        getplayer()
        return;
    }
        
    // console.log(store.leagueChoice)
    // store.leagueChoice = '全部赛事'
    playerList.value = []
    totalElements.value = 0
    isSearch.value = false
    searchLeagueSet = []
    // if (store.leagueChoice = '全部赛事')
    // {
    searchLeagueSet.push('英超')
    searchLeagueSet.push('西甲')
    searchLeagueSet.push('意甲')
    searchLeagueSet.push('德甲')
    searchLeagueSet.push('法甲')
    searchLeagueSet.push('中超')
    // }else{
    // searchLeagueSet.push(store.leagueChoice)
    // }
    for (const league of searchLeagueSet) {
        try {
            const response = await axios.get(`${BaseUrl}/info?leagueName=${league}&searchKey=${store.keyword}`)
            console.log(response);
            playerList.value.push(...response.data.data);
            isSearch.value = true
        } catch (err) {
            ElMessage({
                message: '获取失败',
                type: 'error'
            });
            console.error(err);
        }
    }
}
const toplayerInfo = (player) => {
    router.push({
        path: `/Players/${player.id}`
    })
}


const errorEvent = (text) => {
    console.log('test', text)
    ElMessage({
        message: '搜索框为空',
        type: 'error'
    });
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

.list-content {
    min-height: 450px;
}

#playercard {
    display: flex;
    justify-content: flex-start;
    /* 对齐到开始位置 */
    flex-wrap: wrap;
    /* 允许内容换行 */
    gap: 10px;
    /* 项目之间的间距 */
}

.player-item {
    flex: 1 0 22%;
    /* 每个项目占用大约四分之一的宽度 */
    max-width: 22%;
    /* 确保宽度不超过四分之一 */
}
</style>