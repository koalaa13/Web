<template>
    <div class="middle">
        <Sidebar :users="users" :posts="posts"/>
        <main>
            <Index v-if="page === 'Index'" :users="users" :posts="posts" :comments="comments"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <AddPost v-if="page === 'AddPost'"/>
            <EditPost v-if="page === 'EditPost'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <FullPost v-if="page === 'FullPost'" :post="posts[id]" :users="users" :comments="comments"/>
        </main>
    </div>
</template>

<script>
    import Index from './middle/Index';
    import Enter from './middle/Enter';
    import Register from './middle/Register';
    import AddPost from './middle/AddPost';
    import Sidebar from './Sidebar';
    import EditPost from "./middle/EditPost";
    import Users from "./middle/Users";
    import FullPost from "./middle/FullPost";

    export default {
        name: "Middle",
        props: ['users', 'posts', 'comments'],
        data: function () {
            return {
                page: "Index",
                id: 0
            }
        },
        components: {
            FullPost,
            EditPost,
            Index,
            Enter,
            Register,
            Sidebar,
            AddPost,
            Users
        },
        beforeCreate() {
            this.$root.$on("onChangePage", (page, id) => {
                this.page = page;
                this.id = id;
            });
        }
    }
</script>

<style scoped>

</style>
