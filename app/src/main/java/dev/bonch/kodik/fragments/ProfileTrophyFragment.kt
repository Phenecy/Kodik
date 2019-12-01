package dev.bonch.kodik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.bonch.kodik.R
import dev.bonch.kodik.adapters.TrophiesAdapter
import dev.bonch.kodik.models.Trophy

private lateinit var adapter: TrophiesAdapter
private lateinit var trophiesRecycler: RecyclerView
private lateinit var linearLayoutManager: LinearLayoutManager

private var trophiesList: MutableList<Trophy> = Trophy.TrophiesController().trophiesList

class ProfileTrophyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = TrophiesAdapter(trophiesList)
        val view = inflater.inflate(R.layout.fragment_profile_trophy, container, false)
        linearLayoutManager = LinearLayoutManager(container!!.context)
        linearLayoutManager.isSmoothScrollbarEnabled = true
        trophiesRecycler = view.findViewById(R.id.trophies_recycler_view)
        trophiesRecycler.layoutManager = linearLayoutManager
        trophiesRecycler.adapter = adapter
        return view
    }
}