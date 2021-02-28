# My adventure on solving Google Foobar

Right now, just some take aways for you:

- I solve them without looking at any implementation (I did try to understand
  the ideas of level 5, but then fail and decide to learn Group Theory and solve
  it myself instead).

- I don't expect to get an interview from Google, just feel cool to accomplish
  somethings I couldn't have done 6 years ago.

Some lesson learns for myself:

- This adventure would finish much faster if I'm not so lazy to go with a proper
  brute force solution for level 4.2 and level 5.

You can watch [the videos][videos] of me solving first 7 problems within first 4
hours since entering Foobar. The first few minutes of each videos should show
you full problem statements (and I guess that'll be the only interesting bits
for you). By the way, they're just the usual Easy or Medium Leetcode problems
with wordy statements and invisible test cases. If you're comfortable with
Medium levels in Leetcode, just ignore them.

The last 2 problems are interesting.

## Bringing a gun to a guard fight

- [Problem statements and ideas can be found here.][gun_ideas]
- [My solution][my_gun_code]

This one is not so hard, requires a bit of Geometric Calculus, which is
something I'm capable of. But I spend more than 10h thinking and solve an
entirely other problem by mistake: [The Laser gun and mirrored
room][bodyguards]. That problems is about _how many bodyguards do we, as the
Commander, need to block all beams?_. Foobar problem is about _how many vector
can we shoot the Commander when our beam can travel a limited distance?_. Damn!

I recorded the first 1.5 hour on this one, but then throw away the video since I
know I'll need more time than that, and you definitely don't like to watch me
struggling more than 30m anyway.

## Disorderly Escape

This is something I want like to write about (but too lazy right now), because I
spend 22 days can get it over my head, learned a new subject (Group Theory) to
solve it and use tons of papers to tests various ideas.

[Here's the code with detailed reasoning][my_matrix_code].

Some helpful resources for this problem:

- [An introduction to group theory - Tony Gaglione][gt_book]: a good read, clear
  enough to grasp the concepts of Group Theory while short enough to finish it
  in several days (138 pages). [Pinter's A Book of Abstract Algebra][pinter] is
  another good one but it's much longer (400 pages) and don't have free PDF.
- [Essence of Group Theory - videos on Youtube][gt_videos]: those videos offer
  another approach to Group Theory, which help me to strengthen my
  understanding.
- [Python solution by Kody Puebla][kody_python_code]. I only use it to generate
  test cases.

To the people at Google who use it for level 5, thank you, I can't wait for the
next time I need Group Theory in my life!

Now, there's one thing stuck in my mind: **Is there any simple formula for the
answer?**. Math is beautiful and sometime can be surprising. Consider how [Pi
hiding in prime regularities][pi_and_prime] and what [E][e_number] has anything
to do with [Counting Derangement][wiki_derangement], **I strongly believe such
formula exists!**

I still have 3 days left for Foobar, so let see if we could discover that
formula.

<!-- ref -->

[videos]: https://youtube.com/playlist?list=PLcuGpHQxfbJWt_1CbsL8w-knx7ZP9arDk
[gun_ideas]: https://peter-ak.github.io/2020/05/10/Brining_a_gun_to_a_guard_fight.html
[bodyguards]: https://github.com/letientai299/google-foobar-2021
[my_matrix_code]: ./java/src/main/java/com/foobar/disorderlyEscape/DisorderLyEscape.java
[my_gun_code]: ./java/src/main/java/com/foobar/gun/Gun.java
[gt_book]: ./resources/an-intro-to-group-theory-_-Tony-Gaglione.pdf
[kody_python_code]: ./resources/matrix_orbits.py
[pinter]: https://www.amazon.com/Book-Abstract-Algebra-Second-Mathematics/dp/0486474178
[gt_videos]: https://youtube.com/playlist?list=PLDcSwjT2BF_VuNbn8HiHZKKy59SgnIAeO
[wiki_derangement]: https://en.wikipedia.org/wiki/Derangement
[pi_and_prime]: https://www.youtube.com/watch?v=NaL_Cb42WyY
[e_number]: https://en.wikipedia.org/wiki/E_number
