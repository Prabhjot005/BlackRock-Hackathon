Problem : To find out whether nps or funds are beneficial for a particular customer's investment plan.

Main Subproblems : 

1. To design an algorithm to find annual investment from the expenses of a customer considering q periods, p periods and k periods.
2. To calculate the maturity amount after retirement for NPS by taking all interest, tax rebate and inflation in consideration
3. To calculate the maturity amount after retirement for Nifty 50 by taking all interest, tax rebate and inflation in consideration

Algorithmic approach to the problem statement 1: To design an algorithm to find annual investment from the expenses of a customer considering q periods, p periods and k periods.

To solve this problem firstly, we would create a class Events with the following variables : 
* EVENTTYPE : representing transaction, p period start/ end, q period start/end or k period start/end
* Amount :
           For transaction, it will represent Remanent
           For q period, it will represent Fixed Amount
           For q period, it will represent extra amount to be added
           For k period, this value do not has any signification

After this, we would create a orgered Hashmap of DateTime and Event object. 

=> Iterate through all the transactions and create an Event object of transaction remanent with Type TRANSACTION. Then map transaction date with this Event object.

=> Iterate through all the q periods. Create an event object with Type QPERIOD and fixed amount. For the end period also, create a similar event object with type QPERIOD and amount = -1*fixed amount.

=> Iterate through all the p periods. Create an event object with Type PPERIOD and extra amount. For the end period also, create a similar event object with type PPERIOD and amount = -1*extra amount.
Iterate through all the k periods. Create an event object with TYPE KPERIOD and amount = 1.For the end period, create a similar event object with type KPERIOD and amount = -1.

Now since we have inserted all the timed event including transactions and periods, we'll iterate them in ascending order in HashMap. [ Assuming that payload contains all the valid transactions and periods only].

While traversing, we'll track each type of event in the following way : 

1. For K periods, we'll maintain a variable activeKPeriods, initialized with 0. It represents, current timestamp falls in how many k periods.

2. For P Periods with extras, we'll maintain a vaiable psum where we'll track that the total extra sum needs to be added to the rem. [ Assuming that P periods can be overlapping and the final extras would be the additional of all. If it needs to be maximum of all, then we need to maintain an array of P extras and the max of this Array. In this case, each time a P period ends, if the ending period extra amount = max amount, then max needs to be recalculated from the stored array.]

3. For Q periods, we'll again need to maintain an array QFixedAmtList whose last inserted element would replace the remanent.

Now, we'll traverse the Hashmap orderly in ascending order : 

1. If event type is KPERIOD, add the amount to the activeKPeriods. Amount is 1 for start period and -1 for end period. Hence, our active k periods count will be maintained.
2. If the event type is P period, add the amount to the psum. If its the start of the period, amount is +ve and hence added in the extras. If it is end period, amount is negative and hence removed from the psum.
3. If the event type is Q Period: If amount is positive, insert the amount at the end of the QFixedAmtList. If it is negative, remove the amount (positive) from the array.
   [ It has a limitation since each operation would take O(n) operations. To fix this, solution can be reformed with HashMap ].
4. If the event type is transaction, the investment (initialized with Amount which was remanent) needs to be calculated as per below :
    * If activeKPeriods == 0, investment = 0, else the following steps,
    * If array size of QFixedAmtList > 0, replace the investment with last value of the array i.e. latest fixed value.
    * Add the psum to the investment

Hence after traversing the whole Hashmap, we'll get the total sum invested from each Expense.

Total investment = min(investment, 10% of wage, 200000);

Subproblem 2. To calculate the maturity amount after retirement for NPS by taking all interest, tax rebate and inflation in consideration

Calculate the tax on the income accoring to the slabs.

With the value exceeding the amount of each slab, tax will added on the value coming under that slab 
i.e if between 7-10 LPA, TAX% is 12%
and between 10-14, tax% is 15%,

so for wage of 12 lpa, tax would be 12% on 3L + 15% on 2L.

In the same way calculate the tax on the investment also and find the tax rebate.

On the final amount calculated after compound interest, calculate the inflation.

Subproblem 3. To calculate the maturity amount after retirement for Nifty by taking all interest, tax rebate and inflation in consideration

Similar to the above, calculate the tax and the interest and then find the inflation on the final amount. Return the resulting amount in the response.


TIME COMPLEXITY : 

Worst Case O(n^2) if QFixedAmtList is used.
Average Case O(n log n) 


Docker image : 

docker pull prabhjotw005/blackrock-challenge:3.apis

Port : 8080

Sample APIs : 
http://localhost:8080/blackrock/challenge/v1/transactions:filter
