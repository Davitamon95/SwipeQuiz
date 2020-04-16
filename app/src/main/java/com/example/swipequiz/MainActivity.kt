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

    private fun initViews(){
        tvRecycler.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        tvRecycler.adapter = viewAdapter
        tvRecycler.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

        //Add Questions here
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

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

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

    private fun correctAnswerSwiped(questionCorrect: Boolean){
        if(questionCorrect){
            Toast.makeText(this, getString(R.string.correctFeedback), Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(this, getString(R.string.incorrectFeedback),Toast.LENGTH_SHORT).show()
        }
    }

}
