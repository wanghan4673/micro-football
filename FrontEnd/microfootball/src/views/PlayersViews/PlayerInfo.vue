<template>
    <el-icon @click="back" style="margin-top: 30px; margin-left: 50px;">
        <Back />
    </el-icon>
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
    <div style="width:100%;display: grid; place-items: center; padding: 25px;">
        <div style="width:70%;">
            <a-table :columns="columns" :data-source="seasonInfos" bordered>
                <template #bodyCell="{ column, text }">
                    <template v-if="column.dataIndex === 'seasonNum'">
                        <a>{{ text }}</a>
                    </template>
                </template>
            </a-table>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute,useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const playerId = route.params.id;
const photo = ref('');
const name = ref('');
const age = ref('');
const height = ref('');
const weight = ref('');
const country = ref('');
const birth = ref('');
const seasonInfos = ref([]);

const back = () => {
    router.back();
}
const columns = [
    {
        title: '赛季',
        dataIndex: 'seasonNum',
    },
    {
        title: '联盟',
        dataIndex: 'leagueName',
    },
    {
        title: '队伍',
        dataIndex: 'teamName',
    },
    {
        title: '位置',
        dataIndex: 'position',
    },
    {
        title: '进球数',
        dataIndex: 'goals',
    },
    {
        title: '黄牌',
        dataIndex: 'yellow',
    },
    {
        title: '红牌',
        dataIndex: 'red',
    },
];

onMounted(async () => {
    // 获取数据
    const res = await axios.get(`/api/player/detail?playerId=${playerId}`);
    const data = res.data.data;
    photo.value = data.photo;
    name.value = data.name;
    age.value = data.age;
    height.value = data.height;
    weight.value = data.weight;
    country.value = data.country;
    birth.value = data.birth;
    seasonInfos.value = data.seasonInfos;
});
</script>


<style scoped>
.photo {
    display: grid;
    place-items: center;
    padding: 25px
}

.baisc-info {
    display: grid;
    place-items: center;
    padding: 25px
}
</style>