package mr.lalic.playground.data

interface XoItem {
    fun getXoId(): Long
    fun getXoPicture(): String
    fun getXoName(): String
}

data class Epg(val id: Long, val picturePath: String, val name: String) : XoItem {

    override fun getXoId() = id
    override fun getXoPicture() = picturePath
    override fun getXoName() = name
}

data class Vod(val id: Long, val picturePath: String, val name: String) : XoItem {
    override fun getXoId() = id

    override fun getXoPicture() = picturePath

    override fun getXoName() = "VOD_$name"
}
