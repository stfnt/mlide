package com.baimaisu.mlide.diskcache

abstract class DiskCacheStrategy {
    abstract fun isDataCacheable(dataSource: DataSource):Boolean
    /**
     *
     *  glide 的 decode 是一个针对 InputStream --》 Bitmap 的过程
     *  这里并不知道InputStream 是从那里来的
     *  有可能是Remote 也有可能是 DISK_CACHE_RESOURCE
     *  所以这每次生成资源的时候，都会做判断，检查该资源是否可以缓存
     *  这里的isResourceCacheable的方法做判断
     *
     *  假如资源不是 Disk_Resource_Cache 或者 Memory_Cache  都是可以缓存的
     */
    abstract fun isResourceCacheable(dataSource: DataSource):Boolean

    abstract fun decodeFromData():Boolean
    abstract fun decodeFromResource():Boolean


    /**
     *  cache remote data and resource
     *  cache local only resource
     */
    val ALL = object : DiskCacheStrategy(){

        override fun isDataCacheable(dataSource: DataSource):Boolean {
            return dataSource == DataSource.REMOTE
        }


        override fun isResourceCacheable(dataSource: DataSource):Boolean {
            return dataSource == DataSource.REMOTE
                    || dataSource == DataSource.DISK_DATA_CACHE
                    || dataSource == DataSource.LOCAL
        }

        override fun decodeFromData(): Boolean {
            return true
        }

        override fun decodeFromResource(): Boolean {
            return true
        }

    }

    val NONE = object : DiskCacheStrategy(){
        override fun isDataCacheable(dataSource: DataSource):Boolean {
            return false
        }


        override fun isResourceCacheable(dataSource: DataSource):Boolean {
            return false
        }

        override fun decodeFromData(): Boolean {
            return false
        }

        override fun decodeFromResource(): Boolean {
            return false
        }
    }

    val DATA = object : DiskCacheStrategy(){
        override fun isDataCacheable(dataSource: DataSource):Boolean {
            return dataSource == DataSource.LOCAL
                    || dataSource == DataSource.REMOTE
        }


        override fun isResourceCacheable(dataSource: DataSource):Boolean {
            return false
        }

        override fun decodeFromData(): Boolean {
            return true
        }

        override fun decodeFromResource(): Boolean {
            return false
        }
    }

    val RESOURCE = object : DiskCacheStrategy(){
        override fun isDataCacheable(dataSource: DataSource):Boolean {
            return false
        }


        override fun isResourceCacheable(dataSource: DataSource):Boolean {
            return dataSource == DataSource.LOCAL
                    || dataSource == DataSource.REMOTE
                    || dataSource == DataSource.DISK_DATA_CACHE
        }

        override fun decodeFromData(): Boolean {
            return false
        }

        override fun decodeFromResource(): Boolean {
            return true
        }
    }
}

