package com.example.swipequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questions = arrayListOf<Questions>()
    private val viewAdapter = ViewAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    /**
     *Methode voor initialiseren van de start layout
     */
    private fun initViews(){
        tvRecycler.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        tvRecycler.adapter = viewAdapter
        tvRecycler.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

        //Hier kunnen vragen worden toegevoegd

        questions.add(Questions(getString(R.string.valVSvar), false))
        questions.add(Questions(getString(R.string.ECTS), false))
        questions.add(Questions(getString(R.string.KotlinJava), true))
        questions.add(Questions(getString(R.string.whenSwitch), true))
        questions.add(Questions(getString(R.string.extravraag), true))

        createItemTouchHelper().attachToRecyclerView(tvRecycler)
    }

    private fun createItemTouchHelper(): ItemTouchHelper{
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            /**
             * methode om eht swipen te controleren op goed of fout
             */

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                /**
                 * hier wordt gecontroleerd of de posistie links of rechts is
                 * en daarmee of het goed of fout is om de juiste message te tonen
                 */
                when (direction){
                    ItemTouchHelper.LEFT ->
                        if(!questions[position].questionTrue){
                            correctAnswerSwiped(true)
                            questions.removeAt(position)
                        } else {
                            correctAnswerSwiped(false)
                        }

                    ItemTouchHelper.RIGHT ->
                        if(questions[position].questionTrue){
                            correctAnswerSwiped(true)
                            questions.removeAt(position)
                        } else {
                            correctAnswerSwiped(false)
                        }
                }

                viewAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }

    /**
     *creeeren van toast message bij goed of fout
     */
    private fun correctAnswerSwiped(questionCorrect: Boolean){
        if(questionCorrect){
            Toast.makeText(this, getString(R.string.correctFeedback), Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(this, getString(R.string.incorrectFeedback),Toast.LENGTH_SHORT).show()
        }
    }

}
