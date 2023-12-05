<template>
    <div id="QC-bg">
        <div style="display: flex;">
            <el-icon @click="back" style="margin-top: 25px; margin-left: 10px;">
                <Back />
            </el-icon>
            <div id="Box">
                <p class="QC-title" style="font-size: larger; margin-top: 20px;">新建帖子</p>
            </div>
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
            <!-- <div style="" > -->
            <div>
                <img v-for="img of store.drafts.img" :key="img" :src="convertFileToImageUrl(img)" alt="图片加载失败"
                    style="margin-top: 30px; width: 20%; border: 2px solid #000;display: inline;" />
            </div>
            <!-- </div> -->
            <!-- </div> -->
        </div>
        <div id="Box">
            <button class="submitbutton" type="button" @click="addQuestion" style="width: 10%;">提交</button>
        </div>
    </div>
</template>

<script setup>
import { ref, defineProps } from 'vue'
import WangEditer from '@/components/ForumComponents/WangEditer.vue';

import '../../assets/css/forumbuttoncss.css'
// import QuestionContent from './QuestionContent.vue';
// import QuestionPreview from './QuestionPreview.vue';
import { useGeneralStore } from '@/stores/general';
import UploadFiles from '../../components/ForumComponents/UploadFiles.vue';
import router from '@/router';
const store = useGeneralStore()
import { ElMessage } from 'element-plus'
import axios from 'axios';

const back = () => {
    router.back();
}
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
    justify-content: flex-start;
    padding-left: 20px;
    margin-bottom: 30px;
}

#QC-bg {
    width: 90%;
    background-color: #fff;
    max-width: 1200px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin-top: 10px;
    margin-bottom: 20px;
    padding-left: 30px;
    padding-bottom: 50px;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
}

.QC-title {
    font-size: large;
    font-weight: bold;
}

.el-input {
    width: 30%;
    min-width: 200px;
}</style>