package com.yasir.compose.androidinterviewchallenge.domain.model

data class ImageData(
    val rawData: ByteArray,  // Raw byte array representing the image data
    val metadata: ImageMetadata // Metadata such as image format or compression level
){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ImageData) return false

        return rawData.contentEquals(other.rawData) && metadata == other.metadata
    }

    override fun hashCode(): Int {
        var result = rawData.contentHashCode()
        result = 31 * result + metadata.hashCode()
        return result
    }
}

data class ImageMetadata(
    val format: String,  // e.g., "jpeg", "png"
    val size: Long  // The size of the image in bytes
)
