<template>
    <div id="QC-bg">
        <div id="Box">
            <p class="QC-title" style="font-size: larger;">新建帖子</p>
        </div>
        <div id="Box">
            <p class="QC-title">标题</p>
            <el-input type="text" v-model="store.drafts.title" clearable />
        </div>
        <div id="Box">
            <p class="QC-title">内容</p>
            <WangEditer />
        </div>
        <div id="Box">
            <p class="QC-title">上传图片</p>
            <UploadFiles />
            <div style="margin-top: 30px;" v-for="img of store.drafts.img" :key="img">
                <img :src="convertFileToImageUrl(img)" alt="图片加载失败" style="max-width: 20%; border: 2px solid #000;" />
            </div>
            <!-- </div> -->
        </div>
        <div id="Box">
            <button class="submitbutton" type="button" @click="addQuestion" style="width: 10%;">提交</button>
        </div>
    </div>
</template>

<script setup>
import { ref, defineProps } from 'vue'
import WangEditer from '@/components/WangEditer.vue';

import '../assets/css/forumbuttoncss.css'
// import QuestionContent from './QuestionContent.vue';
// import QuestionPreview from './QuestionPreview.vue';
import { useGeneralStore } from '@/stores/general';
import UploadFiles from '../components/UploadFiles.vue';
import router from '@/router';
const store = useGeneralStore()
import { ElMessage } from 'element-plus'
import axios from 'axios';

const convertFileToImageUrl = (file) => {
    if (file) {
        return URL.createObjectURL(file);
    }
    return '';
};
const uploadFile = async (file) => {
    const formData = new FormData()
    formData.append('file', file);
    try {
        const response = await axios.post(store.api + 'Question/upload', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
        console.log(response)
        if (response.status == 200) {
            const data = response.data
            console.log(data)
            return data
        }
    } catch (e) {
        console.log(e)
    }
}

const addQuestion = async () => {
    try {
        ElMessage({
            message: "还没写",
            type: "success"
        });
    } catch (err) {
        ElMessage({
            message: "提交题目失败",
            type: "error"
        });
        console.log(err);
    }
}
</script>


<style scoped>
#Box {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: felx-start;
    padding-left: 20px;
    margin-bottom: 30px;
}

.QC-title {
    font-size: large;
    font-weight: bold;
}

.el-input {
    width: 30%;
    min-width: 200px;
}
</style>