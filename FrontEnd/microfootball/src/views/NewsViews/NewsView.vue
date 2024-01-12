<template>
    <div class="top-news-container">
        <div class="text-news-container" @scroll="textScroll">
            <div v-for="news, index in textNews" :key="news.id" class="text-news-card" @click="toNewsDetail(news.id)">
                <div class="text-news-title">{{ news.title }}</div>
                <div class="text-news-content">{{ news.content }}</div>
                <div class="text-news-tags">
                    <a-tag v-for="(tag, index) in news.tags" :key="index" :color="getColor(index)" :bordered="false">
                        {{ tag.length > 4 ? `${tag.slice(0, 4)}...` : tag }}
                    </a-tag>
                </div>
            </div>
        </div>
        <div class="video-news-container">
            <div class="change-videonews-button" @click="changeVideoNews()">
                →换一批
            </div>
            <div v-for="news, index in videoNews" :key="news.id" class="video-news-card">
                <img style="border-radius: 1vw;margin-right: 1vw;" :src="news.cover" alt="News Video"
                    @click="toExternalWeb(news.videoUrl)">
                <div class="video-news-detail" @click="toExternalWeb(news.videoUrl)">
                    <div class="video-news-title">{{ news.title }}</div>
                </div>
            </div>
        </div>
    </div>
    <div class="bottom-news-container">
        <div class="pic-news-container">
            <div v-for="news, index in picNews" :key="news.id" class="pic-news-card" @click="toNewsDetail(news.id)">
                <img style="border-radius: 1vw;margin-right: 1vw;" :src="news.picUrl" alt="News Image">
                <div class="pic-news-detail">
                    <div class="pic-news-title">{{ news.title }}</div>
                    <div class="pic-news-content">{{ news.content }}</div>
                </div>
            </div>
        </div>
        <div class="change-picnews-button" @click="changePicNews()">
            →换一批
        </div>
    </div>
</template>

<script setup lang="ts">
import router from '@/router'
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import BackWave from './NewsWave.vue'

const textNews = ref<{ id: number; title: string; content: string; tags: string }[]>([])
const videoNews = ref<{ id: number; title: string; content: string; videoUrl: string; cover: string }[]>([])
const picNews = ref<{ id: number; title: string; content: string; picUrl: string }[]>([])
const pageSize = 5
const picNum = 3
const currentPage = ref(1)

onMounted(() => {
    getTextNews(1, pageSize).then(() => {
        checkMoreTextNews()
    })
    getPicNews()
    getVideoNews()
})

const getTextNews = async (currentPage: number, pageSize: number) => {
    try {
        const response = await axios.get('/api/news/text', {
            params: {
                page: currentPage,
                pageSize: pageSize
            },
        })
        if (response.data.code == 1) {
            textNews.value = [...textNews.value, ...response.data.data.map((item: { id: number; title: string; content: string; tags: string }) => ({
                id: item.id,
                title: item.title,
                content: item.content.replace(/\\n/g, '\n'),
                tags: item.tags ? item.tags.split(',').slice(0, 3) : [],  // 返回的是一个字符串 将其按逗号分隔为一个字符串数组
            }))]
        } else {
            ElMessage({
                message: '获取新闻模块A失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '获取新闻模块A请求发送失败',
            type: 'error',
        })
    }
}

const getVideoNews = async () => {
    try {
        const response = await axios.get('/api/news/video', {
            params: {
                count: picNum,
            },
        })
        if (response.data.code == 1) {
            console.log(response.data.data)
            videoNews.value = response.data.data.map((item: { id: number; title: string; content: string; videoUrls: string; cover: string }) => ({
                id: item.id,
                title: item.title,
                content: item.content.replace(/\\n/g, '\n'),
                videoUrl: item.videoUrls.split(',')[0],
                cover: item.cover,
            }))
        } else {
            ElMessage({
                message: '获取新闻模块B失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '获取新闻模块B请求发送失败',
            type: 'error',
        })
    }
}


const getPicNews = async () => {
    try {
        const response = await axios.get('/api/news/picture', {
            params: {
                count: picNum,
            },
        })
        if (response.data.code == 1) {
            picNews.value = response.data.data.map((item: { id: number; title: string; content: string; picUrls: string }) => ({
                id: item.id,
                title: item.title,
                content: item.content.replace(/\\n/g, '\n'),
                picUrl: item.picUrls.split(',')[0]
            }))
        } else {
            ElMessage({
                message: '获取新闻模块B失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '获取新闻模块C请求发送失败',
            type: 'error',
        })
    }
}

const changePicNews = () => {
    getPicNews()
}

const changeVideoNews = () => {
    getVideoNews()
}

const getColor = (index: number) => {
    // 获取标签颜色
    const colors = ['pink', 'orange', 'cyan'];
    return colors[index % colors.length];
}

const textScroll = (event: any) => {
    const { scrollTop, offsetHeight, scrollHeight } = event.target;
    if (scrollTop + offsetHeight >= scrollHeight * 0.9) {
        currentPage.value++;
        getTextNews(currentPage.value, pageSize);
    }
}

const checkMoreTextNews = () => {
    const container = document.querySelector('.text-news-container');
    if (container && container.scrollHeight <= container.clientHeight) {
        currentPage.value++;
        getTextNews(currentPage.value, pageSize)
    }
}

const toNewsDetail = (id: number) => {
    router.push({
        path: '/news/newsDetail',
        query: {
            id: id.toString()
        }
    })
}

const toExternalWeb = (videoUrl: string) => {
    window.open(videoUrl, '_blank');
}
</script>

<style lang="scss" scoped>
@import '../../assets/font/font.css';
html,
body {
    overflow: hidden;
}

::-webkit-scrollbar {
    display: none;
}

.top-news-container {
    display: grid;
    grid-template-columns: 3fr 1fr;
    height: 70vh;
    gap: 2vw;
    .text-news-container {
        height: 70vh;
        background-color: rgba(247, 247, 248, 0.5);
        display: grid;
        grid-template-rows: repeat(5, 1fr);
        gap: 1vh;
        overflow-y: auto;
    }

    .text-news-container::-webkit-scrollbar {
        width: 0;
    }

    .video-news-container {
        display: grid;
        grid-template-rows: 1fr repeat(3, 3fr);
    }
}

.text-news-card {
    height: 13vh;
    display: flex;
    flex-direction: column;
    padding-inline-start: 2vw;
    cursor: pointer;

    .text-news-title {
        font-family: title;
        font-size: 1.4vw;
        align-self: center;
        margin-top: 0.5vh;
        margin-bottom: 1vh;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        max-width: 20ch;
    }

    .text-news-content {
        font-family: words;
        margin-right: 15%;
        margin-left: 2%;
        font-size: 1vw;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }

    .text-news-tags {
        font-size: 0.8vw;
        align-self: end;
        margin-top: auto;
        margin-right: 1vw;
    }
}

.bottom-news-container {
    height: 20vh;
    margin-top: 2vh;

    .pic-news-container {
        height: 100%;
        width: 90%;
        margin: 0 auto;
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 2vw;
    }
}

.pic-news-card {
    display: grid;
    grid-template-columns: auto 1fr;
    cursor: pointer;

    .pic-news-detail {
        display: grid;
        grid-template-rows: 1fr 2fr;
    }
}

.video-news-card {
    @extend .pic-news-card;

    .video-news-detail {
        display: flex;
        .video-news-title {
            margin-right: 0.5vw;
            font-size: 1.3vw;
            overflow: hidden;
        }
    }
}

.pic-news-card img {
    height: 20vh;
    min-width: 13vw;
    max-width: 13vw;
}

.video-news-card img {
    max-height: 17vh;
}

.pic-news-title {
    font-family: title;
    font-size: 1.5vw;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    display: block;
}

.pic-news-content {
    font-size: 1.2vw;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 4;
    overflow: hidden;
    max-height: 13vh;
}

.change-picnews-button {
    color: blue;
    position: absolute;
    right: 3vw;
    bottom: 20vh;
    transition: transform 0.3s ease;

    &:hover {
        color: red;
        transform: scale(1.1);
        cursor: pointer;
    }
}

.change-videonews-button {
    color: blue;
    z-index: 99;

    &:hover {
        color: red;
        cursor: pointer;
    }
}
</style>