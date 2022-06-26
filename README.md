# <p align=center> PecodeTest App <p>

- [x] - UI as per Figma mock up.
- [x] - "Create new notification" button - creates new notification with number of the fragment where it was created.
- [x] - "+" button - adds new fragment with updated number
- [x] - "-" button - removes last fragment in the list and removes created notifications of that fragment
- [x] - ViewPager2 support with ability to swipe right/left between fragments
- [x] - Clicking on notification will open fragment with the same number where it was created and all fragments that precede that number. (Also added option to keep all fragments, see CounterActivity.kt doc text)
- [x] - Fragments are persistent throughout App reopening

P.S.
First time working with ViewPager on Android. Had to learn it on the fly. Seems ok though.
One thing that bothers me - passing ViewModel from Activity to RecyclerView. From what I've read - this is not really the best approach to this,
and I personally never had necessity of doing this in any projects I worked on before.
However, I had to achieve correct project structure since I was looking to implement the fragment persistence throughout App restarts.
I feel like there is better approach to this, but this would require more research.