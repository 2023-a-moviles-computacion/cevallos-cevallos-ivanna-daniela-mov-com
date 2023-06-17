import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

object WriteFile {
    /**
     * This class shows how to write file in Kotlin
     * @param args
     * @throws IOException
     */
    @JvmStatic
    fun main(args: Array<String>) {
        val data = "I am writing this String to File in Kotlin"
        writeUsingBufferedWriter(data, 3)
        println("Done")
    }

    /**
     * Use BufferedWriter when number of write operations are more
     * It uses internal buffer to reduce real IO operations and saves time
     * @param data
     * @param noOfLines
     */
    private fun writeUsingBufferedWriter(data: String, noOfLines: Int) {
        val folder = File("Results")
        val file = File(folder, "sample.txt")
        var fr: FileWriter? = null
        var br: BufferedWriter? = null
        val dataWithNewLine = data + System.getProperty("line.separator")
        try {
            folder.mkdirs() // Create the folder if it doesn't exist
            fr = FileWriter(file)
            br = BufferedWriter(fr)
            for (i in noOfLines downTo 1) {
                br.write(dataWithNewLine)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                br?.close()
                fr?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
