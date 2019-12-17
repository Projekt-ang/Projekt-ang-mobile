package com.example.myapplication.apiclient.service

import com.example.myapplication.apiclient.model.ExerciseSearch
import com.example.myapplication.apiclient.model.QuestionBackup
import kotlin.random.Random

class ExerciseSearchService {
    companion object {
        fun getExcerciseList() : ExerciseSearch {
            val arr: ArrayList<ExerciseSearch> = ArrayList()
            val readingTest1 = ExerciseSearch(
                    "Every time you're online, you are bombarded by pictures, articles, links and videos trying to tell their story. Unfortunately, not all of these stories are true. Sometimes they want you to click on another story or advertisement at their own site, other times they want to upset people for political reasons. These days it's so easy to share information. These stories circulate quickly, and the result is … fake news.\n" +
                    "\n" +
                    "There is a range of fake news: from crazy stories which people easily recognise to more subtle types of misinformation. Experts in media studies and online psychology have been examining the fake news phenomenon. Read these tips, and don't get fooled!\n" +
                    "\n" +
                    "1. Check the source\n" +
                    "Look at the website where the story comes from. Does it look real? Is the text well written? Are there a variety of other stories or is it just one story? Fake news websites often use addresses that sound like real newspapers, but don't have many real stories about other topics. If you aren't sure, click on the 'About' page and look for a clear description of the organisation.\n" +
                    "\n" +
                    "2. Watch out for fake photos\n" +
                    "Many fake news stories use images that are Photoshopped or taken from an unrelated site. Sometimes, if you just look closely at an image, you can see if it has been changed. Or use a tool like Google Reverse Image search. It will show you if the same image has been used in other contexts.\n" +
                    "\n" +
                    "3. Check the story is in other places\n" +
                    "Look to see if the story you are reading is on other news sites that you know and trust. If you do find it on many other sites, then it probably isn't fake (although there are some exceptions), as many big news organisations try to check their sources before they publish a story. \n" +
                    "\n" +
                    "4. Look for other signs\n" +
                    "There are other techniques that fake news uses. These include using ALL CAPS and lots of ads that pop up when you click on a link. Also, think about how the story makes you feel. If the news story makes you angry, it's probably designed to make you angry.\n" +
                    "\n" +
                    "If you know these things about online news, and can apply them in your everyday life, then you have the control over what to read, what to believe and most importantly what to share. If you find a news story that you know is fake, the most important advice is: don't share it!",
                    null,
                    arrayOf(
                        QuestionBackup("What is the best title for the text?", 1,
                            arrayOf("Experts share dangers of fake news",
                                    "Experts share top tips for resisting fake news",
                                    "How to create fake news: a guide",
                                    "Tips on how to read the news online")
                        ),
                        QuestionBackup("Which reason is NOT given for an online fake news story?", 2,
                            arrayOf("To convince people of a political view",
                                "To make people angry or sad",
                                "To plant a virus in your computer")
                        )
                    )
            )
            val readingTest2 = ExerciseSearch(
                "", "https://www.youtube.com/watch?v=_QdPW8JrYzQ",
                arrayOf(
                    QuestionBackup("What kink of spam email, or junk mail did James receive?", 1,
                        arrayOf("A misleading message from someone you know whose email account has been hacked.",
                                "A counterfeit message that looks reliable that attempts to trick you into supplying your personal information.",
                                "An unsolicited commercial email message sent in bulk")
                    ),
                    QuestionBackup("Does James believe he will receive gold?", 1,
                        arrayOf("Yes", "No")
                    ),
                    QuestionBackup("How much gold does James want Solomon to ship to him?", 2,
                        arrayOf("25 kilograms",
                                "50 kgs",
                                "a metric ton")
                    )
                )
            )
            arr.add(readingTest1)
            arr.add(readingTest2)
            return if (Random.nextBoolean()) readingTest1 else readingTest2
        }
    }
}