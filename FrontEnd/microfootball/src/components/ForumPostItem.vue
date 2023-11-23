<template>
    <div id='PI-bg'>
        <div id="PostItem">
            <p class="post-title">{{ props.post.title }}</p>
            <el-text line-clamp="4" class="post-contain">{{ props.post.content }}</el-text>
        </div>
        <div id="ImageContainer" v-if="props.post.img">
            <img id="PostImg" :src="props.post.img[0]" />
        </div>
    </div>
</template>
  
<script setup>
const props = defineProps({
    post: Object,
});
</script>
  
<style lang="scss" scoped>
#PI-bg {
    width: 90%;
    padding-left: 20px;
    height: 200px;
    display: flex;
    align-items: flex-start;
    background-color: #efefef80;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.2);
    // border-bottom: 1px solid #00000043;
}

#PostItem {
    display: flex;
    flex-direction: column;
    width: 50%;
    word-wrap: break-word;
    padding: 10px;
}

.post-title {
    font-size: larger;
    font-style: normal;
    font-weight: 400;
    color: #1599e0;
    margin-bottom: 8px;
}

.post-contain {
    font-size: medium;
    font-style: normal;
    font-weight: 400;
    margin-bottom: 20px;
    width: 100%;
}

#ImageContainer {
    display: flex;
    justify-content: center;
    align-items: center;
    // border: 2px solid #000;
    height: 90%;
    padding: 10px;
}

#PostImg {
    max-width: 100%;
    max-height: 100%;
    // height: auto;
    border: none;
    opacity: 0.8;
    border-radius: 5px;
}
</style>
  