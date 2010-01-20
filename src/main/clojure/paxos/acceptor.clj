(ns paxos.acceptor)

(def current-proposal-number (ref 0))
(def current-proposal-value (ref nil))

(defn reset-acceptor []
  (dosync
    (ref-set current-proposal-number 0)
    (ref-set current-proposal-value nil)))

(defn proposal-inform [proposal-number proposal-value]
  (list proposal-number proposal-value))

(defn proposal-promise [proposal-number proposal-value]
  (dosync
    (alter current-proposal-number (fn [old new] new) proposal-number)
    (proposal-inform proposal-number proposal-value)))

(defn proposal-accept [proposal-number proposal-value]
  (dosync
    (alter current-proposal-number (fn [old new] new) proposal-number)
    (alter current-proposal-value (fn [old new] new) proposal-value)
    (proposal-inform proposal-number proposal-value)))

(defn prepare-request [proposal-number]
  (dosync
    (if (> proposal-number @current-proposal-number)
      (proposal-promise proposal-number @current-proposal-value)
      (proposal-inform @current-proposal-number @current-proposal-value))))

(defn accept-request [proposal-number proposal-value]
  (dosync
    (if (< proposal-number @current-proposal-number)
      (proposal-inform @current-proposal-number @current-proposal-value)
      (proposal-accept proposal-number proposal-value))))
