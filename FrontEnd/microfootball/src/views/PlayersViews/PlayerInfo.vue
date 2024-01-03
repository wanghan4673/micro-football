<template>
    <div class="photo">
        <img :src="photo">
    </div>
    <div class="baisc-info">
        <a-descriptions title="基本信息" bordered>
            <a-descriptions-item label="姓名">{{ name }}</a-descriptions-item>
            <a-descriptions-item label="年龄">{{ age }}</a-descriptions-item>
            <a-descriptions-item label="身高">{{ height }}</a-descriptions-item>
            <a-descriptions-item label="体重">{{ weight }}</a-descriptions-item>
            <a-descriptions-item label='国家'>{{ country }}</a-descriptions-item>
            <a-descriptions-item label="生日">{{ birth }}</a-descriptions-item>
        </a-descriptions>
    </div>

    <div class="season-info">
        <a-table :columns="columns" :data-source="seasonInfos" bordered>
            <template #bodyCell="{ column, text }">
            <template v-if="column.dataIndex === 'seasonNum'">
                <a>{{ text }}</a>
            </template>
            </template>
            <template #title>Season</template>
        </a-table>

    </div>



</template>

<script setup>
import { useRoute } from 'vue-router';
import {ref, onMounted} from 'vue'
import axios from 'axios'

const route = useRoute();
console.log('route',route)
const playerId = route.params.id;
const photo = ref('')
const name = ref('')
const age = ref('')
const height = ref('')
const weight = ref('')
const country = ref('')
const birth = ref('')
let seasonInfos = []
const columns = [
    {
        title:'赛季',
        dataIndex:'seasonNum'
    },
    {
        title:'联盟',
        dataIndex:'leagueName',
    },
    {
        title:'队伍',
        dataIndex:'teamName'
    },
    {
        title:'位置',
        dataIndex:'position',
    },
    {
        title:'进球数',
        dataIndex:'goals'
    },
    {
        title:'黄牌',
        dataIndex:'yellow'
    },
    {
        title:'红牌',
        dataIndex:'red'
    }
]

onMounted(async ()=>{
    //初始化
    //获取数据
    // const res = {"code":1,"msg":"success","data":{"id":276,"name":"Neymar","firstName":"Neymar","lastName":"da Silva Santos Júnior","age":31,"height":"175 cm","weight":"68 kg","country":"Brazil","photo":"https://media-4.api-sports.io/football/players/276.png","birth":"1992-02-05","seasonInfos":[{"seasonNum":"2013","teamName":"Barcelona","teamLogo":"https://media-4.api-sports.io/football/teams/529.png","leagueName":"La Liga","leagueLogo":"https://media-4.api-sports.io/football/leagues/140.png","position":"Attacker","goals":9,"assists":null,"passes":null,"tackles":null,"yellow":6,"red":0},{"seasonNum":"2014","teamName":"Barcelona","teamLogo":"https://media-4.api-sports.io/football/teams/529.png","leagueName":"La Liga","leagueLogo":"https://media-4.api-sports.io/football/leagues/140.png","position":"Attacker","goals":22,"assists":null,"passes":null,"tackles":null,"yellow":6,"red":0},{"seasonNum":"2015","teamName":"Barcelona","teamLogo":"https://media-4.api-sports.io/football/teams/529.png","leagueName":"La Liga","leagueLogo":"https://media-4.api-sports.io/football/leagues/140.png","position":"Attacker","goals":24,"assists":12,"passes":1584,"tackles":28,"yellow":6,"red":0},{"seasonNum":"2016","teamName":"Barcelona","teamLogo":"https://media-4.api-sports.io/football/teams/529.png","leagueName":"La Liga","leagueLogo":"https://media-4.api-sports.io/football/leagues/140.png","position":"Attacker","goals":13,"assists":11,"passes":1165,"tackles":null,"yellow":7,"red":0},{"seasonNum":"2017","teamName":"Paris Saint Germain","teamLogo":"https://media-4.api-sports.io/football/teams/85.png","leagueName":"Ligue 1","leagueLogo":"https://media-4.api-sports.io/football/leagues/61.png","position":"Attacker","goals":19,"assists":13,"passes":1063,"tackles":null,"yellow":5,"red":0},{"seasonNum":"2018","teamName":"Paris Saint Germain","teamLogo":"https://media-4.api-sports.io/football/teams/85.png","leagueName":"Ligue 1","leagueLogo":"https://media-4.api-sports.io/football/leagues/61.png","position":"Attacker","goals":15,"assists":7,"passes":797,"tackles":8,"yellow":2,"red":0},{"seasonNum":"2019","teamName":"Paris Saint Germain","teamLogo":"https://media-4.api-sports.io/football/teams/85.png","leagueName":"Ligue 1","leagueLogo":"https://media-4.api-sports.io/football/leagues/61.png","position":"Attacker","goals":13,"assists":6,"passes":704,"tackles":13,"yellow":3,"red":0},{"seasonNum":"2020","teamName":"Paris Saint Germain","teamLogo":"https://media-4.api-sports.io/football/teams/85.png","leagueName":"Ligue 1","leagueLogo":"https://media-4.api-sports.io/football/leagues/61.png","position":"Attacker","goals":9,"assists":5,"passes":916,"tackles":14,"yellow":6,"red":1},{"seasonNum":"2021","teamName":"Paris Saint Germain","teamLogo":"https://media-4.api-sports.io/football/teams/85.png","leagueName":"Ligue 1","leagueLogo":"https://media-4.api-sports.io/football/leagues/61.png","position":"Attacker","goals":13,"assists":6,"passes":1221,"tackles":29,"yellow":10,"red":0}]}}
    const res = await axios.get(`/api/player/detail?playerId=${playerId}`)
    const data = res.data.data
    console.log(data)
    photo.value = data.photo
    name.value = ref(data.firstName + ' ' + data.lastName)
    age.value = ref(data.age)
    height.value = ref(data.height)
    weight.value = ref(data.weight)
    country.value = ref(data.country)
    birth.value = ref(data.birth)
    seasonInfos = ref(data.seasonInfos)
})




</script>


<style scoped>
.photo{
    display: grid;
    place-items: center;
    padding:25px
}
.baisc-info{
    display: grid;
    place-items: center;
    padding:25px

}

</style>