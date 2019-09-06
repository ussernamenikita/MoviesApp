package ru.test.moviesapp.ui

class TextUtils {

    companion object {

        /**
         * Returns characters count which completely
         * fits to [fitSize] and not cropped
         * In case of there is one word
         * in range of [beginIndex] ... [beginIndex]+[fitSize]
         * without some break symbols then return [fitSize]
         * @param beginIndex begin index in [text]
         * @param fitSize size of string, which must fit
         * @return fitSize of characters discarding cropped text.
         * Or [fitSize] if there is there is no break symbols in selected range
         */
        fun discardNotFitted(beginIndex: Int, fitSize: Int, text: CharArray): Int {
            var currentSymbolIndex = beginIndex + fitSize
            if (currentSymbolIndex >= text.size) {
                return fitSize
            }
            var symbol = text[currentSymbolIndex]
            var additionalIndex = 0
            while (currentSymbolIndex > beginIndex && symbol != ' ' && symbol != '\t' && symbol != '\n') {
                currentSymbolIndex--
                symbol = text[currentSymbolIndex]
                additionalIndex = 1
            }
            //if currentSymbolIndex == beginIndex then there is no previous work and we must return fitSize
            return if (currentSymbolIndex == beginIndex) {
                fitSize
            } else {
                currentSymbolIndex - beginIndex + additionalIndex
            }
        }
    }
}