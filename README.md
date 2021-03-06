<h1>Bill Payment Schedule generator</h1>
<h2>Walter Schlosser</h2>
<i>Developing with Java and Eclipse</i>
<hr>
<p>As a small personal project, I decided to write an application that could find the most efficient way to pay bills for the month.  I find the problem to be interesting since I have to decide how to frame it and what makes a payment schedule efficient.  I decided that the app should group bills into 'packets' to pay at once.  The payment schedule would then be a list of however many packets the user specifies so that the cost of the most expensive packet in the schedule is minimized, and all bills are still paid on time.  My phrasing is: <br><br>
<b>Given a list of bills <i>b</i>, partition the bills into <i>k</i> groups such that the maximum cost of a group is minimized and all bills are paid on time.</b><br><br>
I consider the cost of the packet to be the sum of all the bills in it, and the cost of a payment schedule to be the cost of the most expensive packet in it.  This way, the problem is just to find the schedule with minimum cost.</p>
<hr>
<h3>Some things that are working:</h3>
<ul>
<li>Basic ugly GUI!</li>
<li>User can hit the load button, and use a file prompt to select a bills file</li>
<li>User can hit the generate button, pick a number of payment periods, and the best payment groups are displayed</li>
<li>A recursive algorithm to find the best schedule</li>
</ul>
<h3>Some improvements I want to make:</h3>
<ul>
<li>Make the GUI look nicer</li>
<li>A test suite would probably be nice</li>
<li>A little more exception handling would be nice as well</li>
<li>Modify the recursive solution to one that uses dynamic programming - would make it faster for extreme situations with lots of bills and many payment periods</li>
<li>Let user create bills and save them to a file so they don't have to write one in the rigid format</li>
<li>Take income into account in the algorithm</li>
</ul>
