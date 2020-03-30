package utils

object VersionComparatorUtil {


    fun compareVersion(versionA: String, versionB: String): Int {

        val listVersionANumbers = versionA.split(".")
        val listVersionBNumbers = versionB.split(".")

        val iteratorVersionANumbers = versionA.split(".").iterator()
        val iteratorVersionBNumbers = versionB.split(".").iterator()

        while (iteratorVersionANumbers.hasNext() || iteratorVersionBNumbers.hasNext()) {

            if (iteratorVersionANumbers.hasNext() && iteratorVersionBNumbers.hasNext()) {

                val numberVersionA = iteratorVersionANumbers.next().toInt()
                val numberVersionB = iteratorVersionBNumbers.next().toInt()

                if (numberVersionA > numberVersionB) return 1
                else if (numberVersionA < numberVersionB) return -1

            } else if (iteratorVersionANumbers.hasNext()) {

                if (iteratorVersionANumbers.next().toInt() != 0) return 1

            } else if (iteratorVersionBNumbers.hasNext()) {

                if (iteratorVersionBNumbers.next().toInt() != 0) return -1

            }

        }

        return 0
    }
}

// Reference https://www.programcreek.com/2014/03/leetcode-compare-version-numbers-java/