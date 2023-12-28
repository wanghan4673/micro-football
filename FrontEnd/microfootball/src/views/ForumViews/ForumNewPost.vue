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
            <p class="QC-title">标签</p>
            <div class="tag-box">
                <button class="tag" v-for="tag in taglist" :key="tag" @click="chosetag(tag)" :class="{ 'selected-tag': tags.includes(tag) }">
                    {{ tag }}
                </button>
            </div>
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
            <button class="submitbutton" type="button" @click="addQuestion" style="width: 10vw;">提交</button>
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

let taglist = ref(['英超','西甲','意甲','德甲','法甲','中超','同济']);
const tags = ref([]);

const chosetag = (tag) =>{
    if (tags.value.includes(tag)) {
    // 如果标签已存在于tags列表中，移除它
    tags.value = tags.value.filter((t) => t !== tag);
  } else {
    // 否则添加标签到tags列表中
    tags.value.push(tag);
  }
}

const back = () => {
    router.back();
}
const convertFileToImageUrl = (file) => {
    if (file) {
        return URL.createObjectURL(file);
    }
    return '';
};
const uploadFile = async (file,postid) => {
    const formData = new FormData()
    let token = localStorage.getItem('token')
    formData.append('file', file);
    try {
        const response = await axios.post('/api/forum/postimg?postid='+postid, formData, {
            headers: {
                'token': `${token}`,
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
    let response
    let tagstring = ref("")
    let token = localStorage.getItem('token')
    for(const tag of tags.value){
        tagstring.value+= tag
    }
    try {
        response = await axios.post('/api/forum/post',{
            "content": store.drafts.content,
            "title": store.drafts.title,
            "tags": tagstring.value,
        },{
            headers: {
                'token': `${token}`,
                'Content-Type': 'application/json',
            }
        })
        if(response.status == 200) {
            ElMessage({
            message: "发帖成功",
            type: "success"
        });
        if(store.drafts.img == null)
            router.push('/forum')
        else
            addpostimg(response.data.data.id)
        }
    } catch (err) {
        ElMessage({
            message: "发帖失败",
            type: "error"
        });
        console.log(err);
    }
}

const addpostimg = (postid) =>{
    for(const img of store.drafts.img){
        uploadFile(img,postid)
    }
}
</script>


<style scoped>

.tag-box{
    display: flex;
    justify-content: flex-start;
    gap:2vw
}
.tag {
    padding: 8px; /* 可以根据需要调整内边距 */
    border-radius: 4px; /* 可以根据需要调整边框半径，使其看起来更像色块 */
    color: #000; /* 文字颜色，根据需要调整 */
    text-align: center; /* 文字居中 */
    width: auto; /* 根据需要调整宽度 */
    background-color: #cce8f3;
    border: none; 
}
.tag:hover{
    color: #fff;
    background-color: #358CC1;
}
.selected-tag {
    color: #fff;
  background-color: #358cc1; /* 修改选中状态下的背景颜色 */
}
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
    margin-bottom: 10px;
}

.el-input {
    width: 30%;
    min-width: 200px;
}</style>