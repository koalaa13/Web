<template>
    <div>
        <Post v-for="post in viewPosts" :post="post" :key="post.id"/>
    </div>
</template>

<script>
    import Post from "./Post";

    export default {
        name: "Index",
        components: {Post},
        props: ["posts"],
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id);
            }
        }
    }
</script>

<style scoped>

</style>
