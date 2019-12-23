<template>
    <div class="middle">
        <aside>
            <SidebarPost v-for="post in viewPosts" :post="post" :key="post.id"/>
        </aside>
        <main>
            <Index v-if="page === 'Index'" :posts="posts"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <AddPost v-if="page === 'AddPost'"/>
            <FullPost v-if="page === 'FullPost'" :post="getPostById(id)"/>
        </main>
    </div>
</template>
<script>
    import Index from './middle/Index';
    import Enter from './middle/Enter';
    import Register from './middle/Register';
    import SidebarPost from './SidebarPost';
    import Users from './middle/Users';
    import AddPost from "./middle/AddPost";
    import FullPost from "./middle/FullPost";

    export default {
        name: "Middle",
        props: ["posts", "users"],
        data: function () {
            return {
                page: "Index",
                id: 0
            }
        },
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
            }
        },
        methods: {
            getPostById: function (id) {
                return Object.values(this.posts).filter(post => post.id === id)[0];
            }
        },
        components: {
            FullPost,
            AddPost,
            Index,
            Enter,
            Register,
            SidebarPost,
            Users
        }, beforeCreate() {
            this.$root.$on("onChangePage", (page, id) => {
                this.page = page;
                this.id = id;
            });
        }
    }
</script>

<style scoped>

</style>
