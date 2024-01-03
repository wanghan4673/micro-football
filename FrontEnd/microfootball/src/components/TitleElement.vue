<template>
    <head>
        <link>
    </head>
    
    <div class="element">
        <h1 :v-if='title_show' class = title >{{ title }}</h1>
        <p class = sub-title>{{ text }}</p>
    </div>
</template>

<script>
    import {ref, onMounted} from 'vue'
    export default{
        props:['subTitle', 'mainTitle'],
        setup(props){
            const text = ref('')
            const title = ref('')
            title.value = props.mainTitle
            const subTitle = props.subTitle

            const typeText = () => {
                let index = 0;
                const interval = 50; //打字速度(每个字符间隔时间)

                const timer = setInterval(() => {
                    if(index < subTitle.length) {
                        text.value += subTitle[index];
                        index++
                    } else {
                        clearInterval(timer);
                    }
                }, interval)
            }
            onMounted(() => {
                typeText();
            })
            return {
                text,
                title,
            }
        },
        mounted(){
            setTimeout(()=>{
                const title = document.querySelector('.title')
                title.style.opacity = 1
            },1000)
            return {
            }
            
        }

    }

</script>

<style scoped>

    .title{
        font-size: 54px;
        font-weight:bold;
        color: #010c34;
        text-shadow: 2px 2px 4px rgba(0,0,0,0.2);
        padding-top: 60px;
        opacity: 0;
        transition: opacity 2s;

    }

    .sub-title{
        font-size: 24px;
        color:black;
        text-shadow: 2px 2px 4px rgba(0,0,0,0.2);
        white-space: nowrap;
        overflow: hidden;
        border-right: 2px solid transparent;

        /* CSS 动画效果 */
        animation: typing is steps(40, end), blink-caret 0.5s step-end infinite
    }

    .element{
        display: grid;
        place-items: center;
    }

</style>
